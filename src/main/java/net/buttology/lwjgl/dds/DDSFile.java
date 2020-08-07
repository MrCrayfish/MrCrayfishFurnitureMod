/*
 * Copyright (C) 2018  Magnus Bull
 *
 *  This file is part of dds-lwjgl.
 *
 *  dds-lwjgl is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  dds-lwjgl is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with dds-lwjgl.  If not, see <https://www.gnu.org/licenses/>. 
 */

package net.buttology.lwjgl.dds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/** 
 * A simple class for loading DirectDraw Surface (*.dds) files for LWJGL. DDS files have many variants and so this parser only supports the following:<br/>
 * <ul>
 * <li>Standard 124 byte headers (not extended D3D headers).</li>
 * <li>Compressed textures using DXT1, DXT3 and DXT5 formats.</li>
 * <li>Reads 2D textures with and without mipmaps (though they are discarded).</li>
 * <li>Reads Cubemap textures without mipmaps. Cubemaps with mipmaps appear offset.</li>
 * <li>Does not support volume maps.</li>
 * <li>Does not support legacy formats.</li>
 * </ul>
 * @author Magnus Bull
 */
public class DDSFile {
	protected static final int GL_COMPRESSED_RGBA_S3TC_DXT1_EXT = 0x83f1;
	protected static final int GL_COMPRESSED_RGBA_S3TC_DXT3_EXT = 0x83f2;
	protected static final int GL_COMPRESSED_RGBA_S3TC_DXT5_EXT = 0x83f3;

	/** A 32-bit representation of the character sequence "DDS " which is the magic word for DDS files. */
	private static final int DDS_MAGIC = 0x20534444;

	/** The header information for this DDS document */
	private DDSHeader 		header;
	private DDSHeaderDXT10	headerDXT10;

	/** Arrays of bytes that contain the surface image data */
	private List<ByteBuffer> bdata;

	/** The number of surfaces in the texture */
	private int 			surfaceCount;

	/** The number of mipmap levels in each surface */
	private int				levels;

	/** The compression format for the current DDS document */
	private int				format;

	/** Whether this DDS file is a cubemap or not */
	private boolean			isCubeMap;

	/** Empty constructor */
	public DDSFile() {}

	/**
	 * Loads a DDS file from the given file path.
	 * @param filePath
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public DDSFile(String filePath) throws IOException 
	{
		this.loadFile(new File(filePath));
	}

	/**
	 * Loads a DDS file from the given file.
	 * @param file
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public DDSFile(File file) throws IOException 
	{
		this.loadFile(file);
	}
	
	/**
	 * Loads a DDS file from the given file path.
	 * @param file
	 * @throws IOException 
	 */
	public void loadFile(String file) throws IOException
	{
		this.loadFile(new File(file));
	}


	/**
	 * Loads a DDS file from the given file.
	 * @param file
	 * @throws IOException
	 */
	public void loadFile(File file) throws IOException
	{
		if(!file.isFile())
		{
			throw new IOException("DDS: File not found: " + file.getAbsolutePath());
		}

		FileInputStream fis = new FileInputStream(file);
		try
		{
			loadFile(fis);
		}
		finally
		{
			fis.close();
		}
	}

	/**
	 * Loads a DDS file.
	 * @param fis
	 * @throws IOException 
	 */
	public void loadFile(FileInputStream fis) throws IOException
	{
		bdata = new ArrayList<ByteBuffer>();

		byte[] bMagic = new byte[4];
		fis.read(bMagic);
		if(!isDDSFile(bMagic))
		{
			throw new IOException("Wrong magic word! This is not a DDS file.");
		}

		byte[] bHeader = new byte[124];
		fis.read(bHeader);
		header = new DDSHeader(newByteBuffer(bHeader));

		headerDXT10 = null;
		int blockSize = 16;
		if(header.ddspf.sFourCC.equalsIgnoreCase("DXT1")) 
		{
			format = GL_COMPRESSED_RGBA_S3TC_DXT1_EXT;
			blockSize = 8;
		}
		else if(header.ddspf.sFourCC.equalsIgnoreCase("DXT3")) 
		{
			format = GL_COMPRESSED_RGBA_S3TC_DXT3_EXT;
		}
		else if(header.ddspf.sFourCC.equalsIgnoreCase("DXT5")) 
		{
			format = GL_COMPRESSED_RGBA_S3TC_DXT5_EXT;
		}
		else if(header.ddspf.sFourCC.equalsIgnoreCase("DX10")) 
		{
			byte[] bHeaderDXT10 = new byte[20];
			fis.read(bHeaderDXT10);
			headerDXT10 = new DDSHeaderDXT10(newByteBuffer(bHeaderDXT10));
			format = headerDXT10.getFormat();
		}
		else 
		{
			throw new IOException("Surface format unknown or not supported: " + header.ddspf.sFourCC);
		}

		if(header.hasCaps2CubeMap || (headerDXT10 != null && headerDXT10.isTextureCube))
		{
			surfaceCount = 6;
			isCubeMap = true; 
		}
		else 
		{
			surfaceCount = 1;
			isCubeMap = false;
		}

		if (headerDXT10 != null && headerDXT10.arraySize > 1)
		{
			surfaceCount *= headerDXT10.arraySize;
		}

		levels = 1;
		if (header.hasFlagMipMapCount)
			levels = Math.max(1, header.dwMipMapCount);

		for(int i = 0; i < surfaceCount; i++)
		{
			for(int j = 0; j < levels; j++)
			{
				int size = calculateSize(blockSize, header.dwWidth >> j, header.dwHeight >> j);
				byte[] bytes = new byte[size];

				fis.read(bytes);
				bdata.add(newByteBuffer(bytes));
			}
		}
	}

	public static boolean isDDSFile(byte[] bMagic)
	{
		return ByteBuffer.wrap(bMagic).order(ByteOrder.LITTLE_ENDIAN).getInt() == DDS_MAGIC;
	}

	private int calculateSize(int blockSize, int width, int height) {
		return Math.max(1, ((height + 3) / 4)) * calculatePitch(blockSize, width);
	}

	private int calculatePitch(int blockSize, int width) {
		return Math.max(1, ((width + 3) / 4)) * blockSize;
	}

	/** Creates a new ByteBuffer and stores the data within it before returning it. */
	public static ByteBuffer newByteBuffer(byte[] data) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(data.length).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public int getWidth() {
		return header.dwWidth;
	}

	public int getHeight() {
		return header.dwHeight;
	}

	/**
	 * Gets the main surface data buffer - usually the first full-sized image.
	 * @return
	 */
	public ByteBuffer getBuffer() {
		return getBuffer(0);
	}

	/**
	 * Gets a specific mipmap level from the main surface.
	 * If specified outside the range of available mipmaps, the closest one is returned.
	 */
	public ByteBuffer getBuffer(int level) {
		level = Math.min(Math.min(header.dwMipMapCount, level), Math.max(level, 0));
		return this.bdata.get(level);
	}

	public int getMipMapCount() {
		return this.levels;
	}

	/**
	 * Gets a specific mipmap level from a specific surface.
	 * If specified outside the range of available surfaces, the closest one is returned.
	 */
	public ByteBuffer getBuffer(int level, int surface) {
		level = Math.min(Math.min(header.dwMipMapCount, level), Math.max(level, 0));
		surface = Math.min(Math.min(surfaceCount, surface), Math.max(surface, 0));
		return this.bdata.get(level * (surface + 1));
	}

	public int getSurfaceCount() {
		return this.surfaceCount;
	}

	public ByteBuffer getCubeMapPositiveX() {
		if(!isCubeMap) return null;
		return getBuffer(0, 1);
	}

	public ByteBuffer getCubeMapNegativeX() {
		if(!isCubeMap) return null;
		return getBuffer(0, 1);
	}

	public ByteBuffer getCubeMapPositiveY() {
		if(!isCubeMap) return null;
		return getBuffer(0, 2);
	}

	public ByteBuffer getCubeMapNegativeY() {
		if(!isCubeMap) return null;
		return getBuffer(0, 3);
	}

	public ByteBuffer getCubeMapPositiveZ() {
		if(!isCubeMap) return null;
		return getBuffer(0, 4);
	}

	public ByteBuffer getCubeMapNegativeZ() {
		if(!isCubeMap) return null;
		return getBuffer(0, 5);
	}

	public ByteBuffer getCubeMapMipPXLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 1);
	}

	public ByteBuffer getCubeMapMipNXLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 2);
	}

	public ByteBuffer getCubeMapMipPYLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 3);
	}

	public ByteBuffer getCubeMapMipNYLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 4);
	}

	public ByteBuffer getCubeMapMipPZLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 5);
	}

	public ByteBuffer getCubeMapMipNZLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 6);
	}

	public int getFormat() {
		return format;
	}

	public boolean isCubeMap() {
		return isCubeMap;
	}
}
