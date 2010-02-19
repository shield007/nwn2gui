package org.stanwood.nwn2.gui.icons;

/*
 * UnsupportedImageTypeException.java
 * Copyright (c) 2003 Reality Interactive, Inc.  
 *   See bottom of file for license and warranty information.
 * Created on Sep 28, 2003
 */

/**
 * <p>Thrown to indicate that an image was not of a known and supported format.</p>
 * 
 * @author Rob Grzywinski <a href="mailto:rgrzywinski@realityinteractive.com">rgrzywinski@realityinteractive.com</a>
 * @version $Id$
 * @since 1.0
 */
public class UnsupportedImageTypeException extends ImageException
{
 	private static final long serialVersionUID = 8085339344902355394L;

	/**
     * @see ImageException#ImageException() 
     */
    public UnsupportedImageTypeException()
    {
        super();
    }

    /**
     * @see ImageException#ImageException(java.lang.String) 
     */
    public UnsupportedImageTypeException(final String message)
    {
        super(message);
    }

    /**
     * @see ImageException#ImageException(java.lang.String, java.lang.Throwable) 
     */
    public UnsupportedImageTypeException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
    
    /**
     * @see ImageException#ImageException(java.lang.Throwable) 
     */
    public UnsupportedImageTypeException(final Throwable cause)
    {
        super(cause);
    }
}
// =============================================================================
/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */