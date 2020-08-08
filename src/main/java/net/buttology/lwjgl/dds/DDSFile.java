/*
 * Copyright (C) 2020  Magnus Bull
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
import static org.lwjgl.opengl.EXTTextureCompressionS3TC.*;
import static org.lwjgl.opengl.EXTTextureCompressionRGTC.*;

/** 
 * Can load DirectDraw Surface (*.dds) texture files for use in LWJGL.
 * 
 * @author Magnus Bull
 * @version 2.0.0
 */
public class DDSFile {
	/**
	 * A 32-bit representation of the character sequence <code>"DDS "</code> which is the magic word for DDS files
	 */
	private static final int DDS_MAGIC = 0x20534444;

	/**
	 * The header information for this DDS document 
	 */
	private DDSHeader			header;
	private DDSHeaderDXT10		headerDXT10;
	
	/**
	 * Arrays of bytes that contain the surface image data
	 */
	private List<ByteBuffer>	bdata;

	/**
	 * The number of surfaces in the texture
	 */
	private int 				surfaceCount;

	/**
	 * The number of mipmap levels in each surface
	 */
	private int					levels;

	/** 
	 * The compression format for the current DDS document
	 */
	private int					format;

	/**
	 * Whether this DDS document is a cubemap or not
	 */
	private boolean				isCubeMap;
	
	public DDSFile() {}

	/**
	 * Loads a DDS file from the given file path.
	 * @param filePath
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public DDSFile(String filePath) throws IOException, FileNotFoundException {
		this(new File(filePath));
	}

	/**
	 * Loads a DDS file from the given file.
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public DDSFile(File file) throws IOException, FileNotFoundException {
		if(!file.isFile()) {
			throw new FileNotFoundException("DDS: File not found " + file.getAbsolutePath());
		}
		FileInputStream fis = new FileInputStream(file);
		this.loadFile(fis);
	}

	/**
	 * Loads a DDS file from the given file stream.
	 * @param fis
	 * @throws IOException
	 */
	public DDSFile(FileInputStream fis) throws IOException
	{
		this.loadFile(fis);
	}
	
	/**
	 * Loads a DDS file from the given file path.
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void loadFile(String file) throws IOException, FileNotFoundException {
		this.loadFile(new File(file));
	}

	/**
	 * Loads a DDS file from the given file.
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void loadFile(File file) throws IOException
	{
		if(!file.isFile())
		{
			throw new FileNotFoundException("DDS: File not found: " + file.getAbsolutePath());
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
		if(fis.available() < 128) {
			throw new IOException("Invalid file size. Must be at least 128 bytes.");
		}

		byte[] bMagic = new byte[4];
		fis.read(bMagic);
		if(!isDDSFile(bMagic))
		{
			throw new IOException("Invalid DDS file. Magic number does not match.");
		}

		byte[] bHeader = new byte[124];
		fis.read(bHeader);
		header = new DDSHeader(newByteBuffer(bHeader));
		
		int blockSize = 16;
		headerDXT10 = null;
		switch(header.ddspf.sFourCC) {
		case "DXT1":
			format = GL_COMPRESSED_RGB_S3TC_DXT1_EXT;
			blockSize = 8;
			break;
		case "DXT3":
			format = GL_COMPRESSED_RGBA_S3TC_DXT3_EXT;
			break;
		case "DXT5":
			format = GL_COMPRESSED_RGBA_S3TC_DXT5_EXT;
			break;
		case "ATI1":
			format = GL_COMPRESSED_RED_RGTC1_EXT;
			blockSize = 8;
			break;
		case "ATI2":
			format = GL_COMPRESSED_RED_GREEN_RGTC2_EXT;
			break;
		case "DX10":
			byte[] bHeaderDXT10 = new byte[20];
			fis.read(bHeaderDXT10);
			headerDXT10 = new DDSHeaderDXT10(newByteBuffer(bHeaderDXT10));
			format = headerDXT10.getFormat();
			blockSize = headerDXT10.getBlockSize();
			break;
		default:
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

		bdata = new ArrayList<ByteBuffer>();
		for(int i = 0; i < surfaceCount; i++)
		{
			for(int j = 0; j < levels; j++)
			{
				int size = calculateSizeBC(blockSize, header.dwWidth >> j, header.dwHeight >> j);
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

	/**
	 * Calculate the linear size of the document based on the given block size. Applies to block compression formats.
	 * @param blockSize
	 * @return linear size in bytes
	 */
	private int calculateSizeBC(int blockSize, int width, int height) {
		return Math.max(1, ((height + 3) / 4)) * calculatePitchBC(blockSize, width);
	}

	/**
	 * Calculate the pitch of the document based on the given block size. Applies to block compression formats.
	 * @param blockSize
	 * @return pitch in bytes
	 */
	private int calculatePitchBC(int blockSize, int width) {
		return Math.max(1, ((width + 3) / 4)) * blockSize;
	}

	/**
	 * Creates a new ByteBuffer and stores the data within it before returning it.
	 */
	public static ByteBuffer newByteBuffer(byte[] data) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(data.length).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	/**
	 * Get the width of this image document.
	 * @return width in pixels
	 */
	public int getWidth() {
		return header.dwWidth;
	}

	/**
	 * Get the width of specified mipmap level.
	 * @param level
	 * @return width in pixels
	 */
	public int getWidth(int level) {
		return Math.max(header.dwWidth >> level, 1);
	}

	/**
	 * Get the height of this image document.
	 * @return height in pixels
	 */
	public int getHeight() {
		return header.dwHeight;
	}

	/**
	 * Get the height of specified mipmap level.
	 * @param level
	 * @return height in pixels
	 */
	public int getHeight(int level) {
		return Math.max(header.dwHeight >> level, 1);
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
		level = Math.min(Math.min(levels - 1, level), Math.max(level, 0));
		return this.bdata.get(level);
	}

	/**
	 * Get the number of mipmap levels of this document.
	 * @return number of mipmaps
	 */
	public int getMipMapCount() {
		return this.levels;
	}

	/**
	 * Gets a specific mipmap level from a specific surface.
	 * If specified outside the range of available surfaces, the closest one is returned.
	 */
	public ByteBuffer getBuffer(int level, int surface) {
		level = Math.min(Math.min(levels - 1, level), Math.max(level, 0));
		surface = Math.min(Math.min(surfaceCount - 1, surface), Math.max(surface, 0));
		return this.bdata.get(level * (surface + 1));
	}

	public int getSurfaceCount() {
		return this.surfaceCount;
	}

	/**
	 * Get the surface buffer for the positive X direction of a cubemap. If this document is not a cubemap, null is returned.
	 * @return positive X buffer, or null if not cubemap.
	 */
	public ByteBuffer getCubeMapPositiveX() {
		if(!isCubeMap) return null;
		return getBuffer(0, 0);
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
		return getBuffer(level, 0);
	}

	public ByteBuffer getCubeMapMipNXLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 1);
	}

	public ByteBuffer getCubeMapMipPYLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 2);
	}

	public ByteBuffer getCubeMapMipNYLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 3);
	}

	public ByteBuffer getCubeMapMipPZLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 4);
	}

	public ByteBuffer getCubeMapMipNZLevel(int level) {
		if(!isCubeMap) return null;
		return getBuffer(level, 5);
	}

	/**
	 * Gets the compression format used for this DDS document.
	 * @return integer representing the format in LWJGL.
	 */
	public int getFormat() {
		return format;
	}

	/**
	 * Whether this DDS document is a cubemap or not.
	 * @return true if cubemap, else false
	 */
	public boolean isCubeMap() {
		return isCubeMap;
	}
}
