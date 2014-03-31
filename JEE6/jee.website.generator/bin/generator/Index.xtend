package generator

class Index  extends AbstractWebsite {
	
	override path() {
		"index.html"
	}
	
	override contents() '''
	<!--Container-->
	<br/><br/><br/>
	<div id="header_wrapper" class="container">
		<div class="flexslider image-slider">
			<div class="span5 slide">
				<h2>
					Java Enterprise Development made <strong>Easy!</strong>
				</h2>
				<br />
				<p>
					Don't repeat yourself by implementing the same things ever and ever.
					Use a simple DSL to generate an CRUD Prototype of your application,
					change and extend it to your needs.
				</p>
				<a href="download.html" class="btn_download"></a>
			</div>
			<div class="span6">
				<div class="introduction">
					<a href="#"><img src="images/videoThumbnail.png"
						alt="image" style="min-height: 420px; min-width: 580px;"/></a>
					<a href="http://www.youtube.com/watch?v=spnmScZUJHc" rel="prettyPhoto" title="Vorstellung des JEE6 Generators" class="zoom zoom_icon"></a>
				</div>
			</div>
		</div>
	</div>
	
	<div id="intro">
		<div class="container">
		</div>
	</div>

	<div id="features">
		<div class="container">
			<div class="row">
				<div class="span12">
					<br /> <br />
					<h1>A Selection Of Supported Features</h1>
					<br />
				</div>
				<div class="span4">
					<div class="thumb">
						<a href="#"><img class="screenshot"
							src="images/feature2_syntax_coloring.png" alt="Image" /></a> <a
							href="http://vimeo.com/12824833" data-rel="prettyPhoto"
							title="Syntax Coloring" class="zoom zoom_icon"></a>
					</div>
					<h4>Syntax Coloring</h4>
					<p>
						Out of the box, the editor supports <b>syntax coloring based
							on the lexical structure</b> and the <b>semantic data</b> of your
						files. Users are free to customize the highlighting and configure
						their favorite styles.
					</p>
				</div>
				<div class="span4">
					<div class="thumb">
						<a href="#"><img class="screenshot"
							src="images/feature2_content_assist.png" alt="Image" /></a> <a
							href="http://vimeo.com/12824786" data-rel="prettyPhoto"
							title="Content Assist" class="zoom zoom_icon"></a>
					</div>
					<h4>Content Assist</h4>
					<p>An Xtext editor proposes valid code completions at any place
						in the document, helping your users with the syntactical details
						of your language.</p>
				</div>
				<div class="span4">
					<div class="thumb">
						<a href="#"><img class="screenshot"
							src="images/feature2_quick_fix.png" alt="Image" /></a> <a
							href="http://vimeo.com/12824853" data-rel="prettyPhoto"
							title="Validation and Quick Fixes" class="zoom zoom_icon"></a>
					</div>
					<h4>Validation and Quick Fixes</h4>
					<p>
						Xtext has outstanding support for static analysis and validation
						of your models. It has never been so easy to define constraints to
						tackle <b>errors and warnings</b> in your code instantaneously.
						With custom <b>quick fixes</b> you can correct these with a single
						keystroke.
					</p>
				</div>
				<div class="span4">
					<div class="thumb">
						<a href="#"><img class="screenshot"
							src="images/feature2_xbase2.png" alt="Image" /></a> <a
							href="http://vimeo.com/26150627" data-rel="prettyPhoto"
							title="Java Integration" class="zoom zoom_icon"></a>
					</div>
					<h4>Advanced Java Integration</h4>
					<p>If your language targets the JVM, you'll love the Java
						support Xtext provides. You get fully statically typed expressions
						right embedded in your DSL and can refer to any Java type.</p>
				</div>
				<div class="span4">
					<div class="thumb">
						<a href="#"><img class="screenshot"
							src="images/feature2_emf_integration.png" alt="Image" /></a> <a
							href="http://vimeo.com/12824804" data-rel="prettyPhoto"
							title="Integration with arbitrary Eclipse tools"
							class="zoom zoom_icon"></a>
					</div>
					<h4>Integration with other Eclipse tools</h4>
					<p>
						Xtext provides a rich API to work with resources. Therefore
						developing additional <b>graphical and structural views</b> is
						comparably easy.
					</p>
				</div>
				<div class="span4">
					<div class="thumb">
						<a href="#"><img class="screenshot"
							src="images/feature2_advanced_workbench.png" alt="Image" /></a> <a
							href="http://vimeo.com/12824746" data-rel="prettyPhoto"
							title="More IDE Features" class="zoom zoom_icon"></a>
					</div>
					<h4>More IDE Features</h4>
					<p>
						Xtext's advanced Eclipse integration goes far beyond the editor. <b>You
							will not feel any difference between your language and Java.</b>
					</p>
				</div>
			</div>
		</div>
	</div>
	'''
	
}