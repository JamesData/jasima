/*******************************************************************************
 * Copyright (c) 2010, 2014 Torsten Hildebrandt and jasima contributors
 *
 * This file is part of jasima, v1.1.
 *
 * jasima is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jasima is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jasima.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id$
 *******************************************************************************/
package jasima_gui.launcher;

import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

public class Arguments {

	public static ArrayList<String> parseArgs(ILaunchConfiguration config) throws CoreException {
		String args = config.getAttribute(ATTR_PROGRAM_ARGUMENTS, "");
		ArrayList<String> retVal = new ArrayList<String>();
		while (!(args = args.trim()).isEmpty()) {
			int delim;
			if (args.startsWith("\"")) {
				args = args.substring(1);
				delim = args.indexOf('"');
				if (delim < 0) {
					retVal.add(args);
					break;
				}
			} else {
				delim = args.indexOf(' ');
				if (delim < 0) {
					retVal.add(args);
					break;
				}
			}
			retVal.add(args.substring(0, delim));
			args = args.substring(delim + 1);
		}
		return retVal;
	}

	public static final String addQuotes(String str) {
		return str.indexOf(' ') == -1 ? str : ("\"" + str + "\"");
	}

	private StringBuilder builder = null;

	public void append(String arg) {
		appendUnquoted(addQuotes(arg));
	}

	public void appendUnquoted(String arg) {
		if (builder == null) {
			builder = new StringBuilder();
		} else {
			builder.append(' ');
		}
		builder.append(arg);
	}

	@Override
	public String toString() {
		if (builder == null) {
			return "";
		} else {
			return builder.toString();
		}
	}

}
