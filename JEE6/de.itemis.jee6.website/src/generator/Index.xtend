package generator

class Index extends AbstractWebsite {

	override path() {
		"index.html"
	}

	override contents() '''
		«homeContainer»
	'''
	
	def homeContainer() '''
		<div id="homeContainer">
			«stage»
		</div>
	'''

	def stage() '''
		<div id="stage">
			<div class="video centering">
				<div class="content box" style="left: 0px;">
					<a href="http://www.youtube.com/watch?v=spnmScZUJHc" rel="prettyPhoto" title="Vorstellung des JEE Generators">
						<img src="images/videoThumbnail.png" alt="" style="height: 350px; width: 450px;"/>
						<div class="overlay"></div>
					</a>
				</div>
		
				<div class="tour_next">
					<h1>JEE Generator</h1>
					<p><strong>Java Enterprise Entwicklung leicht gemacht!</strong></p>
					<a href="features.html">weiter lesen</a>
				</div>
			</div>
		</div>
	'''
}
