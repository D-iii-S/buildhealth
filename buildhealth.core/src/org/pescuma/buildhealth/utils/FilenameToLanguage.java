package org.pescuma.buildhealth.utils;

import static org.apache.commons.io.FilenameUtils.*;

import java.util.HashMap;
import java.util.Map;

public class FilenameToLanguage {
	
	// Copied from cloc
	
	private static final Map<String, String> extensions = new HashMap<String, String>();
	static {
		extensions.put("abap", "ABAP");
		extensions.put("ac", "m4");
		extensions.put("ada", "Ada");
		extensions.put("adb", "Ada");
		extensions.put("ads", "Ada");
		extensions.put("adso", "ADSO/IDSM");
		extensions.put("ahk", "AutoHotkey");
		extensions.put("am", "make");
		extensions.put("ample", "AMPLE");
		extensions.put("as", "ActionScript");
		extensions.put("dofile", "AMPLE");
		extensions.put("startup", "AMPLE");
		extensions.put("asa", "ASP");
		extensions.put("asax", "ASP.Net");
		extensions.put("ascx", "ASP.Net");
		extensions.put("asm", "Assembly");
		extensions.put("asmx", "ASP.Net");
		extensions.put("asp", "ASP");
		extensions.put("aspx", "ASP.Net");
		extensions.put("master", "ASP.Net");
		extensions.put("sitemap", "ASP.Net");
		extensions.put("awk", "awk");
		extensions.put("bash", "BourneAgainShell");
		extensions.put("bas", "VisualBasic");
		extensions.put("bat", "DOSBatch");
		extensions.put("BAT", "DOSBatch");
		extensions.put("build.xml", "Ant");
		extensions.put("cbl", "COBOL");
		extensions.put("CBL", "COBOL");
		extensions.put("c", "C");
		extensions.put("C", "C++");
		extensions.put("cc", "C++");
		extensions.put("ccs", "CCS");
		extensions.put("cfc", "ColdFusionCFScript");
		extensions.put("cfm", "ColdFusion");
		extensions.put("cl", "Lisp/OpenCL");
		extensions.put("clj", "Clojure");
		extensions.put("cljs", "ClojureScript");
		extensions.put("cls", "VisualBasic");
		extensions.put("CMakeLists.txt", "CMake");
		extensions.put("cob", "COBOL");
		extensions.put("COB", "COBOL");
		extensions.put("coffee", "CoffeeScript");
		extensions.put("config", "ASP.Net");
		extensions.put("cpp", "C++");
		extensions.put("cs", "C#");
		extensions.put("csh", "CShell");
		extensions.put("css", "CSS");
		extensions.put("ctl", "VisualBasic");
		extensions.put("cxx", "C++");
		extensions.put("d", "D");
		extensions.put("da", "DAL");
		extensions.put("dart", "Dart");
		extensions.put("def", "Teamcenterdef");
		extensions.put("dmap", "NASTRANDMAP");
		extensions.put("dpr", "Pascal");
		extensions.put("dsr", "VisualBasic");
		extensions.put("dtd", "DTD");
		extensions.put("ec", "C");
		extensions.put("el", "Lisp");
		extensions.put("erl", "Erlang");
		extensions.put("exp", "Expect");
		extensions.put("f77", "Fortran77");
		extensions.put("F77", "Fortran77");
		extensions.put("f90", "Fortran90");
		extensions.put("F90", "Fortran90");
		extensions.put("f95", "Fortran95");
		extensions.put("F95", "Fortran95");
		extensions.put("f", "Fortran77");
		extensions.put("F", "Fortran77");
		extensions.put("fmt", "OracleForms");
		extensions.put("focexec", "Focus");
		extensions.put("frm", "VisualBasic");
		extensions.put("gnumakefile", "make");
		extensions.put("Gnumakefile", "make");
		extensions.put("go", "Go");
		extensions.put("groovy", "Groovy");
		extensions.put("h", "C/C++Header");
		extensions.put("H", "C/C++Header");
		extensions.put("hh", "C/C++Header");
		extensions.put("hpp", "C/C++Header");
		extensions.put("hrl", "Erlang");
		extensions.put("hs", "Haskell");
		extensions.put("htm", "HTML");
		extensions.put("html", "HTML");
		extensions.put("i3", "Modula3");
		extensions.put("idl", "IDL");
		extensions.put("ism", "InstallShield");
		extensions.put("pro", "IDL");
		extensions.put("ig", "Modula3");
		extensions.put("il", "SKILL");
		extensions.put("ils", "SKILL++");
		extensions.put("inc", "PHP/Pascal");
		extensions.put("ino", "ArduinoSketch");
		extensions.put("pde", "ArduinoSketch");
		extensions.put("itk", "Tcl/Tk");
		extensions.put("java", "Java");
		extensions.put("jcl", "JCL");
		extensions.put("jl", "Lisp");
		extensions.put("js", "Javascript");
		extensions.put("jsp", "JSP");
		extensions.put("ksc", "Kermit");
		extensions.put("ksh", "KornShell");
		extensions.put("lhs", "Haskell");
		extensions.put("l", "lex");
		extensions.put("lsp", "Lisp");
		extensions.put("lisp", "Lisp");
		extensions.put("lua", "Lua");
		extensions.put("m3", "Modula3");
		extensions.put("m4", "m4");
		extensions.put("makefile", "make");
		extensions.put("Makefile", "make");
		extensions.put("met", "Teamcentermet");
		extensions.put("mg", "Modula3");
		extensions.put("mli", "ML");
		extensions.put("ml", "ML");
		extensions.put("ml", "OCaml");
		extensions.put("mli", "OCaml");
		extensions.put("mly", "OCaml");
		extensions.put("mll", "OCaml");
		extensions.put("m", "MATLAB/ObjectiveC/MUMPS");
		extensions.put("mm", "ObjectiveC++");
		extensions.put("wdproj", "MSBuildscripts");
		extensions.put("csproj", "MSBuildscripts");
		extensions.put("mps", "MUMPS");
		extensions.put("mth", "Teamcentermth");
		extensions.put("oscript", "LiveLinkOScript");
		extensions.put("pad", "Ada");
		extensions.put("pas", "Pascal");
		extensions.put("pcc", "C++");
		extensions.put("perl", "Perl");
		extensions.put("pfo", "Fortran77");
		extensions.put("pgc", "C");
		extensions.put("php3", "PHP");
		extensions.put("php4", "PHP");
		extensions.put("php5", "PHP");
		extensions.put("php", "PHP");
		extensions.put("plh", "Perl");
		extensions.put("pl", "Perl");
		extensions.put("PL", "Perl");
		extensions.put("plx", "Perl");
		extensions.put("pm", "Perl");
		extensions.put("pom.xml", "Maven");
		extensions.put("pom", "Maven");
		extensions.put("p", "Pascal");
		extensions.put("pp", "Pascal");
		extensions.put("psql", "SQL");
		extensions.put("py", "Python");
		extensions.put("pyx", "Cython");
		extensions.put("qml", "QML");
		extensions.put("rb", "Ruby");
		extensions.put("resx", "ASP.Net");
		extensions.put("rex", "OracleReports");
		extensions.put("rexx", "Rexx");
		extensions.put("rhtml", "RubyHTML");
		extensions.put("rs", "Rust");
		extensions.put("s", "Assembly");
		extensions.put("S", "Assembly");
		extensions.put("scala", "Scala");
		extensions.put("sbl", "SoftbridgeBasic");
		extensions.put("SBL", "SoftbridgeBasic");
		extensions.put("sc", "Lisp");
		extensions.put("scm", "Lisp");
		extensions.put("sed", "sed");
		extensions.put("ses", "PatranCommandLanguage");
		extensions.put("pcl", "PatranCommandLanguage");
		extensions.put("ps1", "PowerShell");
		extensions.put("sh", "BourneShell");
		extensions.put("smarty", "Smarty");
		extensions.put("sql", "SQL");
		extensions.put("SQL", "SQL");
		extensions.put("sproc.sql", "SQLStoredProcedure");
		extensions.put("spoc.sql", "SQLStoredProcedure");
		extensions.put("spc.sql", "SQLStoredProcedure");
		extensions.put("udf.sql", "SQLStoredProcedure");
		extensions.put("data.sql", "SQLData");
		extensions.put("tcl", "Tcl/Tk");
		extensions.put("tcsh", "CShell");
		extensions.put("tk", "Tcl/Tk");
		extensions.put("tpl", "Smarty");
		extensions.put("vala", "Vala");
		extensions.put("vhd", "VHDL");
		extensions.put("VHD", "VHDL");
		extensions.put("vhdl", "VHDL");
		extensions.put("VHDL", "VHDL");
		extensions.put("vba", "VisualBasic");
		extensions.put("VBA", "VisualBasic");
		extensions.put("vbp", "VisualBasic");
		extensions.put("vb", "VisualBasic");
		extensions.put("VB", "VisualBasic");
		extensions.put("vbw", "VisualBasic");
		extensions.put("vbs", "VisualBasic");
		extensions.put("VBS", "VisualBasic");
		extensions.put("webinfo", "ASP.Net");
		extensions.put("xml", "XML");
		extensions.put("XML", "XML");
		extensions.put("mxml", "MXML");
		extensions.put("build", "NAntscripts");
		extensions.put("vim", "vimscript");
		extensions.put("xaml", "XAML");
		extensions.put("xsd", "XSD");
		extensions.put("XSD", "XSD");
		extensions.put("xslt", "XSLT");
		extensions.put("XSLT", "XSLT");
		extensions.put("xsl", "XSLT");
		extensions.put("XSL", "XSLT");
		extensions.put("y", "yacc");
		extensions.put("yaml", "YAML");
		extensions.put("yml", "YAML");
	};
	
	private static final Map<String, String> filenames = new HashMap<String, String>();
	static {
		filenames.put("Makefile", "make");
		filenames.put("makefile", "make");
		filenames.put("gnumakefile", "make");
		filenames.put("Gnumakefile", "make");
		filenames.put("CMakeLists.txt", "CMake");
		filenames.put("build.xml", "Ant/XML");
		filenames.put("pom.xml", "Maven/XML");
	};
	
	public static String detectLanguage(String filename) {
		if (filename == null)
			return "";
		
		String result = filenames.get(getName(filename));
		if (result != null)
			return result;
		
		result = extensions.get(getExtension(filename));
		if (result != null)
			return result;
		
		return "";
	}
}
