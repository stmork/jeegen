package generator

class Index extends AbstractWebsite {

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
					Java Enterprise Entwicklung <strong>leicht</strong> gemacht!
				</h2>
				<br />
				<p>
					Wiederholen Sie nicht sich selbst, wenn Sie dieselben Dinge wieder und wieder neu implementieren.
					Benutzen Sie eine einfache DSL, um damit einen CRUD-Prototypen Ihrer Web-Applikation zu erzeugen.
					Ändern und erweitern Sie Ihre Web-Applikation nach Ihren Bedürfnissen.
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
			<h1>Was ist der JEE6 Generator?</h1>

			<p>
				Der JEE-6Generator ist in der Lage, eine komplette JEE6-Web-Applikation aus einem einfachen Modell zu generieren.<br />
				Das Framework wurde mit Xtext realisiert und ist als Eclipse-Plugin verfügbar.
			</p>
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
			</div>
		</div>
	</div>
	'''

}
