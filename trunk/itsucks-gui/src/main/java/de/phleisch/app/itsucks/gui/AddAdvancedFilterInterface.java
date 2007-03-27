/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.gui;

import de.phleisch.app.itsucks.filter.RegExpJobFilter;

public interface AddAdvancedFilterInterface {

	public abstract void addAdvancedFilterRule(
			RegExpJobFilter.RegExpFilterRule pRule);

}