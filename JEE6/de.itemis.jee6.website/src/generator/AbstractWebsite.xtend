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
			<meta name="description"
				content="«websiteDescription»">
			<meta name="author" content="">
			«stylesheets»
			«javaScriptDocumentStart»
		</head>
		<body>
			«navBar»
		«contents»
			«quickLinks»
			«javaScriptAtTheEnd»
		</body>
		</html>
	'''
	
	def websiteDescription() { 
		'Die Website des JEE6 Generator Projects'
	}

	def websiteTitle() {
		'JEE6 Generator'
	}
	
	def javaScriptDocumentStart() '''
		<script src="js/twitter.js" type="text/javascript"></script>
		<script src="js/jquery-1.7.1.min.js"></script>
		<script src="js/jquery.prettyPhoto.js" type="text/javascript"></script>
			<script type="text/javascript">
		     $(document).ready(function() {
				«jsOnLoad»
		     });
			</script>
	'''
	def CharSequence jsOnLoad() '''
		«IF isPrettyPrint»
			prettyPrint();
		«ENDIF»
		$('a[data-rel]').each(function() {
			$(this).attr('rel', $(this).data('rel'));
		});

		$("a[rel^='prettyPhoto']").prettyPhoto({
			animation_speed: 'fast',
			slideshow: 5000,
			autoplay_slideshow: false,
			opacity: 0.80,
			show_title: true,
			theme: 'ligh_square',
			overlay_gallery: false,
			social_tools: false
		});
		«IF isOutline»
			$('#nav-outline > li > a').live('click', function() {        
				$(this).parent().find('ul').slideToggle();      
			});
		«ENDIF»
		«IF isPopover()»
			$('.has-popover').popover();
		«ENDIF»
	'''

	def protected boolean isPrettyPrint() { false }
	def protected boolean isOutline() { true }
	def protected boolean isPopover() { true }
	
	def Iterable<Pair<String,String>> topLevelMenu() {
		newArrayList(
			'download.html' -> 'Download',
			'features.html' -> 'Features',
			'documentation.html' -> 'Documentation',
			'community.html' -> 'Community',
			'aboutus.html' -> 'Über uns',
			'http://xtext.org' -> 'Xtext',
			'http://www.eclipse.org' -> 'Eclipse.org'
		)
	}

	def navBar() '''
		<!-- Navbar -->
		<div class="navbar navbar-fixed-top"
			style="border-bottom: 1px solid #000;">
			<div class="navbar-inner">
				<div class="container">
					<a class="btn btn-navbar" data-toggle="collapse"
						data-target=".nav-collapse"> <span class="icon-bar"></span> <span
						class="icon-bar"></span> <span class="icon-bar"></span>
					</a> <a class="brand" href="index.html"></a>
					<div class="nav-collapse collapse" style="height: 0px;">
						<ul class="nav">
							«FOR it : topLevelMenu»
							<li «IF path == key»class="active"«ENDIF»><a href="«key»">«value»</a></li>
							«ENDFOR»
						</ul>
					</div>
		
				</div>
			</div>
		</div>
		<!-- Navbar End -->
	'''
	
	def quickLinks() '''
		<div id="extra">
			<div class="inner">
				<div class="container">
					<div class="row">
						<div class="span6">
							<h3 style="padding-top: 0px; margin-top: 0px;">Quick Links</h3>
							<ul class="footer-links clearfix">
								<li><a href="http://itemis.de/">itemis AG</a></li>
								<li><a href="https://www.youtube.com/user/itemisAG">itemis Youtube Kanal</a></li>
							</ul>
							<ul class="footer-links clearfix">
		      					<li><a href="http://www.jboss.org/jbossas">JBoss Application Server 7</a></li>
								<li><a href="http://glassfish.java.net/">Glassfish Application Server</a></li>
							</ul>
						</div>
						<div class="span6">
						</div>
					</div>
				</div>
			</div>
		</div>
	'''

	def javaScriptAtTheEnd() '''
		<!-- Le javascript
		    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		
		<script src="js/bootstrap-transition.js"></script>
		<script src="js/bootstrap-alert.js"></script>
		<script src="js/bootstrap-modal.js"></script>
		<script src="js/bootstrap-dropdown.js"></script>
		<script src="js/bootstrap-scrollspy.js"></script>
		<script src="js/bootstrap-tab.js"></script>
		<script src="js/bootstrap-tooltip.js"></script>
		<script src="js/bootstrap-popover.js"></script>
		<script src="js/bootstrap-button.js"></script>
		<script src="js/bootstrap-collapse.js"></script>
		<script src="js/bootstrap-carousel.js"></script>
		<script src="js/bootstrap-typeahead.js"></script>

		«IF prettyPrint»		
			<!-- include pretty-print files -->
			<script type="text/javascript" src="google-code-prettify/prettify.js"></script>
			<script type="text/javascript" src="google-code-prettify/lang-xtend.js"></script>
		«ENDIF»
		
		<!-- Include the plug-in -->
		<script src="js/jquery.easing.1.3.js" type="text/javascript"></script>
		<script src="js/custom.js" type="text/javascript"></script>
	'''

	
	
	
	def stylesheets() '''
		<!--  styles -->
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<!-- Le fav and touch icons -->
		
		<link rel="shortcut icon" href="images/favicon.ico">
		
		<link href="css/bootstrap.css" rel="stylesheet" type='text/css'>
		<link href="css/bootstrap-responsive.css" rel="stylesheet" type='text/css'>
		<link href="css/style.css" rel="stylesheet" type='text/css'>
		<link href="css/shield-responsive.css" rel="stylesheet" type='text/css'>
		<link href='css/fonts.css' rel='stylesheet' type='text/css'>
		<link href="css/prettyPhoto.css" rel="stylesheet" media="screen" type='text/css'>
		<link href="google-code-prettify/prettify.css" type="text/css" rel="stylesheet"/>
		<!--[if lt IE 9]>
		<link href="css/iebugs.css" rel="stylesheet" type='text/css'>
		<![endif]-->
	'''

	def headline(String title) '''
		<div id="header_wrapper" class="container" >
		</div>
	'''
}