/*
 * #%L
 * JSR-223-compliant JRuby scripting language plugin.
 * %%
 * Copyright (C) 2008 - 2025 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.plugins.scripting.jruby;

import java.io.File;

import org.scijava.app.AppService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.script.AbstractScriptHeader;
import org.scijava.script.ScriptHeader;
import org.scijava.script.ScriptLanguage;

/**
 * Provides a default script header when editing {@link JRubyScriptLanguage}
 * scripts. This automatically imports the {@code imagej.rb} utility script.
 *
 * @author Mark Hiner
 */
@Plugin(type = ScriptHeader.class)
public class JRubyScriptHeader extends AbstractScriptHeader {

	@Parameter
	private AppService appService;

	// -- ScriptHeader Methods --

	@Override
	public String getHeader() {

		String header = null;

		final String imagejPath =
			File.separator + "plugins" + File.separator + "JRuby" + File.separator +
				"imagej.rb";

		if (new File(appService.getApp().getBaseDirectory() + imagejPath).exists())
		{
			header =
				"# @AppService appService\n" +
					"require_relative \"#{$appService.getApp.getBaseDirectory}" +
					imagejPath + "\"";
		}

		return header;
	}

	// -- AbstractScriptHeader methods --

	@Override
	protected Class<? extends ScriptLanguage> handledType() {
		return JRubyScriptLanguage.class;
	}
}
