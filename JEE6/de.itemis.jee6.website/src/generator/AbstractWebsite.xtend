package generator

import java.io.File
import com.google.common.base.Charsets

import static extension com.google.common.io.CharStreams.*
import static extension com.google.common.io.Files.*
import org.eclipse.xtext.xdoc.XdocStandaloneSetup
import java.lang.Iterable
import org.eclipse.xtext.xbase.lib.Pair

abstract class AbstractWebsite implements Resource {
	
	protected new() {
		val injector = standaloneSetup.createInjectorAndDoEMFRegistration
		injector.injectMembers(this)
	}
	
	def XdocStandaloneSetup getStandaloneSetup() {
		new XdocStandaloneSetup()
	}
	
	override generateTo(File targetDir) {
		val file = new File(targetDir, path)
		if (file.exists) {
			print("overwriting ")
		}
		website.write(file.newWriterSupplier(Charsets::UTF_8))
		println("generated '"+file+"'")
	}

	def Iterable<Pair<String,String>> topLevelMenu() {
		newArrayList(
			'index.html' -> 'Startseite',
			'features.html' -> 'Features',
			'aboutus.html' -> 'Über uns',
			'documentation.html' -> 'Dokumentation',
			'download.html' -> 'Download'
		)
	}

	/*
	 * the path relative the website root
	 */
	def String path()
	
	def CharSequence contents()
	
	def website() '''
		<!DOCTYPE html>
		<html lang="en">
		<head>
			<meta charset="utf-8">
			<title>«websiteTitle»</title>
			<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
			<meta name="description" content="«websiteDescription»">
			<meta name="author" content="">
			«stylesheets»
			«javaScriptDocumentStart»
		</head>
		<body class="home">
			<div id="wrap">
				«header»
				«contents»
			</div>
			«footer»
			«javaScriptAtTheEnd»
		</body>
		</html>
	'''
	
	def websiteDescription() { 
		'Die Website des JEE Generator Projects'
	}

	def websiteTitle() {
		'JEE Generator'
	}
	
	def javaScriptDocumentStart() '''
		<!--[if lte IE 7]><link rel="stylesheet" href="css/iehacks.css" /><![endif]-->

		<!--[if lt IE 9]><script src="js/libs/html5shiv.js"></script><![endif]-->
		<script src="js/libs/jquery-1.7.1.min.js"></script>
		<script src="js/libs/modernizr-2.5.3.min.js"></script>
		<script src="js/jquery.prettyPhoto.js" type="text/javascript"></script>
	'''

	def protected boolean isPrettyPrint() { false }
	def protected boolean isOutline() { true }
	def protected boolean isPopover() { true }
	
	def header() '''
		<header>
			<div class="centering">
				<div class="logo">
					<a href="index.html">
						<img src="images/logo.png" height="65" alt="JEE Generator" />
					</a>
				</div>
				«navBar»
			</div>
			«slogan»
		</header>
	'''

	def navBar() '''
		<nav class="menu">
			<ul>
				«FOR it : topLevelMenu»
					<li «IF path == key»«ENDIF»>«IF path == key»<strong>«value»</strong>«ELSE»<a href="«key»">«value»</a>«ENDIF»</li>
				«ENDFOR»
			</ul>
		</nav>
	'''

	def slogan() '''
		<div id="slogan">
			<div class="sloganCentering">
				<h2>
					<strong>Was ist der JEE Generator?</strong>
					Der JEE-6Generator ist in der Lage, eine komplette JEE-Web-Applikation aus einem einfachen Modell zu generieren.
					Das Framework wurde mit Xtext realisiert und ist als Eclipse-Plugin verfügbar. 
				</h2>
			</div>
		</div>
	'''

	def footer() '''
		<footer>
			<div class="centering">
				<nav>
					<ul>
						<li><a href="kontakt.html">Kontakt</a></li>
						<li><a href="legal-notice.html">Impressum</a></li>
					</ul>
				</nav>
		
				<div class="logo">
					<a href="http://www.itemis.de/"><img src="http://www.yakindu.com/resources/img/itemis_logo.png" alt="itemis" /></a>
				</div>
				<p>Copyright &copy; 2012&ndash;2014 <a href="http://www.itemis.de/">itemis AG</a>. Alle Rechte vorbehalten.</p>
				<div class="powered">
					Powered by: <a href="http://www.itemis.de/itemis-ag/services-und-loesungen/language=de/27261/eclipse-modeling"><img class="img-eclipse" src="http://www.yakindu.com/resources/img/eclipse_logo.jpg" alt="eclipse" /></a><a href="http://www.eclipse.org/Xtext/"><img src="http://www.yakindu.com/resources/img/xtext_logo.png" alt="xtext" /></a>
				</div>
			</div>
		</footer>
	'''

	def javaScriptAtTheEnd() '''
		<script src="js/script.js"></script>
		
		<script>
			$(document).ready(function(){
				$("a[rel^='prettyPhoto']").prettyPhoto({
					social_tools: '',
					default_width: 750,
					default_height: 516
				});
			});
		</script>
		«IF prettyPrint»
			<!-- include pretty-print files -->
			<script type="text/javascript" src="google-code-prettify/prettify.js"></script>
			<script type="text/javascript">
				$(document).ready(function() {
					prettyPrint();
				});
			</script>
		«ENDIF»
	'''

	def stylesheets() '''
		<link rel="shortcut icon" href="images/favicon.ico">
		
		<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="de"> <![endif]-->
		<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="de"> <![endif]-->
		<!--[if IE 8]>    <html class="no-js lt-ie9" lang="de"> <![endif]-->
		<!--[if gt IE 8]><!--> <html class="no-js" lang="de"> <!--<![endif]-->
		<link href="google-code-prettify/prettify.css" type="text/css" rel="stylesheet"/>
		<link href="css/general.css" type="text/css" rel="stylesheet"/>
		<link href="css/jeegenerator.css" type="text/css" rel="stylesheet"/>
		<!--[if lt IE 9]>
		<link href="css/iebugs.css" rel="stylesheet" type='text/css'>
		<![endif]-->
	'''
}