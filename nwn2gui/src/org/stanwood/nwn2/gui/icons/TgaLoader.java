package org.stanwood.nwn2.gui.icons;

/*
 * ImageLoader.java
 * Copyright (c) 2003 Reality Interactive, Inc.  
 *   See bottom of file for license and warranty information.
 * Created on Sep 28, 2003
 */

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * <p>A library for image loading using {@link javax.imageio.ImageIO}.</p>
 * 
 * @author Rob Grzywinski <a href="mailto:rgrzywinski@realityinteractive.com">rgrzywinski@realityinteractive.com</a>
 * @version $Id$
 * @since 1.0
 */
public class TgaLoader
{
    /**
     * <p>Display the supported image types for ImageIO to <code>stderr</code>.</p>
     */
    public static void displaySupportedImageTypes()
    {
        final String[] formatNames = ImageIO.getReaderFormatNames();
        if(formatNames.length > 0)
        {
            System.err.println("ImageIO formats:");
            for(int i=0; i<formatNames.length; i++)
            {
                System.err.println("\t" + formatNames[i]);
            }
        } else /* there are no image formats */
        {
            System.err.println("There are no supported ImageIO formats.");
        }
    }

// NOTE:  an example of how to use a BufferedImage with an OpenGL implementation    
//        // get the internal data format and the pixel format from the 
//        // SampleModel of the BufferedImage 
//        // FIXME:  find better way to do this
//        final SampleModel sampleModel = image.getSampleModel();
//        final int internalFormat;
//        final int format;
//        switch(sampleModel.getDataType())
//        {
//            // three components
//            case DataBuffer.TYPE_BYTE:
//                internalFormat = 3;
//                format = GL.GL_RGB;
//                break; 
//
//            // four components
//            case DataBuffer.TYPE_INT:
//                internalFormat = 4; 
//                format = GL.GL_RGBA;
//                break;
//
//            default:
//                throw new UnsupportedImageTypeException("Only TYPE_BYTE and TYPE_INT are supported (" + sampleModel.getDataType() + ").");
//        }
//
//        // upload the texture
//        // TODO:  allow the creation of mip-maps
//        // NOTE:  the pixel data type is always GL_UNSIGNED_BYTE (as that is
//        //        what is output from getPixelData()).
//        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, internalFormat,
//                        image.getWidth(), image.getHeight(),
//                        0 /*no border*/, 
//                        format, GL.GL_UNSIGNED_BYTE, pixelData);

    // =========================================================================
    /**
     * <p>Load a {@link java.awt.image.BufferedImage} from the specified file
     * name relative to the Simulation <code>resource</code> directory.</p>
     * 
     * @param  filename the name of the image to load (relative to the 
     *         <code>resource</code> directory)
     * @return the loaded <code>BufferedImage</code>
     * @throws ImageException if the image file did not exist, could not be
     *         read or 
     */
    public static BufferedImage loadImage(final File filename)
        throws ImageException
    {       
        if(!filename.exists())
        {
            throw new ImageException("The filename \"" + filename + "\" could not be found.");
        } /* else -- the filename was found */

        // set no caching for ImageIO as there should be no rewinds
        // NOTE:  having caching enabled *severely* slows down image reading
        //        performance
        // NOTE:  this does not need to be set each time since it's local to
        //        each thread group.
        ImageIO.setUseCache(false /*don't use file caching*/);

        InputStream inputStream = null;
        ImageInputStream imageStream = null; 
        BufferedImage bufferedImage = null;
        try
        {
            // NOTE:  due to a re-emergence of:
            //           http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6331418
            //        ImageIO.read() cannot be used directly.  This is 
            //        effectively a copy of ImageIO.read() that catches the 
            //        IllegalArgumentException and tries the next available
            //        ImageReader.
            // TODO:  determine if there is a way (via classpath tricks) to set
            //        the order of the service providers so that precedence can
            //        be given to the RI provider

            inputStream = new FileInputStream(filename);
            imageStream = ImageIO.createImageInputStream(inputStream);
            if(imageStream == null)
                throw new ImageException("\"" + filename + "\" could not be loaded.  Probably an unknown format.");
            /* else -- there is an ImageInputStream for the image */
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(imageStream);
            if(!readers.hasNext())
                throw new ImageException("\"" + filename + "\" could not be loaded.  Probably an unknown format.");
            /* else -- there are ImageReader's for the image */

            // try ImageReaders until a non-null BufferedImage is loaded
            ImageReader foundReader = null;
            while(readers.hasNext())
            {
                try
                {                	
                    ImageReader reader = readers.next();
                    if (reader.getFormatName().equals("tga")) {
                    	foundReader = reader;
                    	break;
                    }                    
                    /* else -- the image was not loaded */
                } catch(final IllegalArgumentException iae)
                {
                    // NOTE:  this is explicitly caught for bug:
                    //          http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6331418

                    // try the next ImageReader
                    continue;
                }
            }
            
            if (foundReader!=null) {            
            	foundReader.setInput(imageStream, true/*seek forward only*/, true/*ignore metadata*/);
                
	            bufferedImage = foundReader.read(0, foundReader.getDefaultReadParam());
	            foundReader.dispose();                    	            	           
            }

// NOTE:  intentionally commented out (see above NOTEs)
//            // read the image from the URL
//            bufferedImage = ImageIO.read(imageUrl);
        } catch(IOException ioe)
        {
            // the image could not be read or there was an error reading it
            throw new ImageReadFailedException(ioe);
        } finally
        {
            try
            {
                if(imageStream != null) {
                    imageStream.close();
                }
                /* else -- the ImageStream was never created */
            } 
            catch(final IOException ioe)
            {
                // an error occurred while closing
                // NOTE:  an outer IOException may have been thrown in which case
                //        this exception would shadow it.  It is acceptible in
                //        this case since they are the same exception (though
                //        the potentially more meaningful reason would be lost).
                throw new ImageReadFailedException(ioe);
            }
            
            try
            {
	            if (inputStream!=null) {
	            	inputStream.close();
	            }
            } 
            catch(final IOException ioe)
            {
                // an error occurred while closing
                // NOTE:  an outer IOException may have been thrown in which case
                //        this exception would shadow it.  It is acceptible in
                //        this case since they are the same exception (though
                //        the potentially more meaningful reason would be lost).
                throw new ImageReadFailedException(ioe);
            }
        }

        // if the buffered image is null then it could not be loaded
        if(bufferedImage == null) 
            throw new ImageException("\"" + filename + "\" could not be loaded.  Probably an unknown format.");
        /* else -- the buffered image is loaded */

        // flip the image vertically
        // NOTE:  OpenGL specifies that (0, 0) is in the lower-left corner 
        //        whereas a BufferedImage's (0, 0) is in the *upper*-left corner.
        final AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
                              transform.translate(0, -bufferedImage.getHeight(null));
        final AffineTransformOp op =
            new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        bufferedImage = op.filter(bufferedImage, null);

        return bufferedImage;
    }

    /**
     * <p>Retrieve the image pixel data as a {@link java.nio.ByteBuffer}.</p>
     * 
     * @param  bufferedImage the <code>BufferedImage</code> from which the pixel
     *         data is to be extracted 
     * @return the pixel data stored in a direct allocated <code>ByteBuffer</code>
     * @throws UnsupportedImageTypeException if the <code>BufferedImage</code>
     *         is not of a supported type.
     */
    // FIXME:  javadoc supported types
    public static ByteBuffer getPixelData(final BufferedImage bufferedImage)
        throws UnsupportedImageTypeException
    {
        // based on the SampleModel of the BufferedImage, retrieve the pixel data
        final SampleModel sampleModel = bufferedImage.getSampleModel();
        final ByteBuffer buffer;
        switch(sampleModel.getDataType())
        {
            case DataBuffer.TYPE_BYTE:
            {
                // retrieve the data as bytes
                final byte[] data = ((DataBufferByte)bufferedImage.getRaster().getDataBuffer()).getData();

                // put the data into a ByteBuffer
                buffer = ByteBuffer.allocateDirect(data.length);
                buffer.order(ByteOrder.nativeOrder());
                buffer.put(data, 0, data.length);

                // flip the buffer for consistency
                // NOTE:  flip() not rewind() since the limit is to be preserved
                buffer.flip();

                break;
            }
            case DataBuffer.TYPE_INT:
            {
                // retrieve the data as ints
                final int[] data = ((DataBufferInt)bufferedImage.getRaster().getDataBuffer()).getData();

                // put the data into a ByteBuffer as ints 
                buffer = ByteBuffer.allocateDirect(data.length * 4/*BufferUtils.SIZEOF_INT*/);
                buffer.order(ByteOrder.nativeOrder());
                buffer.asIntBuffer().put(data, 0, data.length);

                // NOTE:  do NOT flip the buffer as the data was put into an
                //        intBuffer which maintains a separate limit, position,
                //        etc. 

                break;
            }
            default:
                // TODO:  "to string" the data type
                throw new UnsupportedImageTypeException("Only TYPE_BYTE and TYPE_INT are supported (" + sampleModel.getDataType() + ").");
        }

        return buffer;
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