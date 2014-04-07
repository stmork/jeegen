package generator

class AboutUs extends AbstractWebsite {

	override path() {
		'community.html'
	}
	
	override contents() '''
	<div id="header_wrapper" class="container" >
	</div>
		<div id="page">
			<div id="team" class="container clearfix"> 
				<h2>Über uns</h2>
		        <hr>
			</div>
		</div>
	'''
	
	def entry(String img, String title, String link, String description) '''
		<div class="row">
			<div class="span1">&nbsp;</div>
			<div class="span9 team">
		    <a href="«link.trim»" class="anchor-in-div"></a>
		    <div class="row">
			  <div class="span1 ">
			    <img src="images/«img.trim»" alt="image" class="image_left">
			  </div>
			  <div class="span2 ">
			    <h3>«title.trim»</h3>
			 	</div>
				<div class="span6 ">
					«description»
				</div>
				</div>
			</div>
		  <div class="span1">&nbsp;</div>
		</div>
	'''
}