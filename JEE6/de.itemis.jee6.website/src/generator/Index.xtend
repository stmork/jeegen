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
	'''

}
