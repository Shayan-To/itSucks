/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 15.01.2007
 */

package de.phleisch.app.itsucks.io;

/**
 * The Metadata interface provides information about current connection of
 * an data retriever.  
 * 
 * @author olli
 *
 */
public interface Metadata {

	/**
	 * Returns the filename of the url object.
	 * @return
	 */
	public String getFilename();
	
}
