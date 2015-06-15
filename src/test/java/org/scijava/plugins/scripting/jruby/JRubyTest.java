/*
 * #%L
 * JSR-223-compliant JRuby scripting language plugin.
 * %%
 * Copyright (C) 2008 - 2015 Board of Regents of the University of
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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.junit.Test;
import org.scijava.Context;
import org.scijava.script.ScriptLanguage;
import org.scijava.script.ScriptModule;
import org.scijava.script.ScriptService;

/**
 * JRuby unit tests.
 * 
 * @author Johannes Schindelin
 */
public class JRubyTest {

	@Test
	public void testBasic() throws InterruptedException, ExecutionException,
		IOException, ScriptException
	{
		final Context context = new Context(ScriptService.class);
		final ScriptService scriptService = context.getService(ScriptService.class);
		final String script = "$x = 1 + 2;";
		final ScriptModule m = scriptService.run("add.rb", script, true).get();
		final Object result = m.getReturnValue();
		assertEquals(3L, result);
	}

	@Test
	public void testLocals() throws ScriptException {
		final Context context = new Context(ScriptService.class);
		final ScriptService scriptService = context.getService(ScriptService.class);

		final ScriptLanguage language = scriptService.getLanguageByExtension("rb");
		final ScriptEngine engine = language.getScriptEngine();
		final String engineClassName = engine.getClass().getName();
		assertEquals("org.jruby.embed.jsr223.JRubyEngine", engineClassName);
		engine.put("hello", 17);
		assertEquals(17L, engine.eval("$hello"));
		assertEquals(17, engine.get("hello"));
	}

	@Test
	public void testParameters() throws InterruptedException, ExecutionException,
		IOException, ScriptException
	{
		final Context context = new Context(ScriptService.class);
		final ScriptService scriptService = context.getService(ScriptService.class);

		final String script = "" + //
			"# @ScriptService ss\n" + //
			"# @OUTPUT String language\n" + //
			"$language = $ss.getLanguageByName(\"ruby\").getLanguageName\n";
		final ScriptModule m = scriptService.run("hello.rb", script, true).get();

		final Object actual = m.getOutput("language");
		final String expected =
			scriptService.getLanguageByName("ruby").getLanguageName();
		assertEquals(expected, actual);

		final Object result = m.getReturnValue();
		assertEquals(expected, result);
	}

}
