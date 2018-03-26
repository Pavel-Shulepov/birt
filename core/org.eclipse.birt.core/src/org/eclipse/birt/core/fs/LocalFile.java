/*******************************************************************************
 * Copyright (c) 2018 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.core.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Provides local file system support.
 */

public class LocalFile implements IFile
{

	private final File file;

	public LocalFile( File localFile )
	{
		this.file = localFile;
	}

	@Override
	public String getName( )
	{
		return file.getName( );
	}

	@Override
	public String getPath( )
	{
		return file.getPath( );
	}

	@Override
	public boolean exists( )
	{
		return file.exists( );
	}

	@Override
	public boolean delete( )
	{
		return file.delete( );
	}

	@Override
	public boolean mkdirs( )
	{
		return file.mkdirs( );
	}

	@Override
	public boolean isDirectory( )
	{
		return file.isDirectory( );
	}

	@Override
	public IFile[] listFiles( )
	{
		if ( !file.exists( ) || !file.isDirectory( ) )
		{
			return new IFile[0];
		}
		File[] files = file.listFiles( );
		IFile[] result = new IFile[files.length];
		for ( int i = 0; i < files.length; i++ )
		{
			result[i] = new LocalFile( files[i] );
		}
		return result;
	}

	@Override
	public OutputStream createOutputStream( ) throws IOException
	{
		return new FileOutputStream( file );
	}

	@Override
	public InputStream createInputStream( ) throws IOException
	{
		return new FileInputStream( file );
	}

	@Override
	public URL toURL( ) throws IOException
	{
		return file.toURI( ).toURL( );
	}

	@Override
	public boolean isAbsolute( )
	{
		return file.isAbsolute( );
	}

}
