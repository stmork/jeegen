package generator

class Download extends AbstractWebsite {

	override path() {
		"download.html"
	}

	def String getLink(String platform) {
		'distro/eclipse-jee6-generator-kepler-' + platform + platform.fileExtension
	}

	def String fileExtension(String platform) {
		if(platform == WIN_32||platform==WIN_64)
			".zip"
		else
			".tar.gz"
	}

	public static val OSX_64 = 'macosx-cocoa-x86_64';
	public static val OSX_32 = 'macosx-cocoa';
	public static val LINUX_64 = 'linux-gtk-x86_64';
	public static val LINUX_32 = 'linux-gtk';
	public static val WIN_64 = 'win32-x86_64';
	public static val WIN_32 = 'win32';

	override contents() '''
		«headline('Download')»
		<div id="page">
			<div class="inner">
				<div class="container clearfix">
					<h2>Download Options</h2>
					<p>
				      <strong>Check the <a href="releasenotes.html">release notes</a>.
				      </strong>
				    </p>
					<hr>
					<!--table one starts-->
					<div class="span1">&nbsp;</div>
				    <div class="span4">
						<div class="pricing">
						    <table>
								<tfoot>
								    <tr>
										<td></td>
								    </tr>
								</tfoot>
								<tbody>
							    <tr>
									<td class="focus">Update Sites <span>Use Update Manager in Eclipse</span></td>
							    </tr>
							    <tr>
							    	<td style="text-align: center;">
							    	<a
							    	rel="popover"
							    	data-content="This is an Eclipse update site URL, which you need to paste into the update manager within Eclipse!"
							    	data-original-title="Right-click and 'Copy Link'"
							    	href="http://jee6-generator.itemis.de/updates/kepler/"
							    	class="has-popover btn btn-primary btn-large"
							    	style="width: 70%;">Kepler</a></td>
							    </tr>
							    <tr>
									<td style="text-align: center;">
									<a
									  rel="popover"
							    	data-content="This is an Eclipse update site URL, which you need to paste into the update manager within Eclipse!"
							    	data-original-title="Right-click and 'Copy Link'"
									  href="http://jee6-generator.itemis.de/updates/juno/"
							    	class="has-popover btn btn-primary btn-large"
							    	style="width: 70%;">Juno</a></td>
							    </tr>
«««							    <tr>
«««									<td style="text-align: center;">
«««									<a
«««										rel="popover"
«««							    	data-content="This is an Eclipse update site URL, which you need to paste into the update manager within Eclipse!"
«««							    	data-original-title="Right-click and 'Copy Link'"
«««										href="http://download.eclipse.org/modeling/tmf/xtext/updates/composite/latest/"
«««							    	class="has-popover btn btn-primary btn-large"
«««							    	style="width: 70%;">Bleeding Edge!</a></td>
«««							    </tr>
							</tbody>
						    </table>
						</div>
				    </div>
				    <div class="span1">&nbsp;</div>
				    <div class="span4">
						<div class="pricing">
						    <table>
								<tfoot>
								    <tr>
										<td></td>
								    </tr>
								</tfoot>
								<tbody>
								    <tr>
										<td class="focus">Full Eclipse <span>Just Download and Unzip</span></td>
								    </tr>
								    <tr>
								    	<td style="text-align: center; white-space: nowrap;">
								    	<a href="«getLink(OSX_64)»"
								    	class="btn btn-primary btn-large" style="width: 45%;">OS X 64 Bit</a>&nbsp;
								    	<a href="«getLink(OSX_32)»"
								    	class="btn btn-primary btn-large"
								    	style="width:20%;">32 Bit</a></td>
								    </tr>
								    <tr>
								    	<td style="text-align: center; white-space: nowrap;">
								    	<a href="«getLink(LINUX_64)»"
								    	class="btn btn-primary btn-large"
								    	style="width: 45%;">Linux 64 Bit</a>&nbsp;
								    	<a href="«getLink(LINUX_32)»"
								    	class="btn btn-primary btn-large"
								    	style="width:20%;">32 Bit</a></td>
								    </tr>
								    <tr>
								    	<td style="text-align: center; white-space: nowrap;">
								    	<a href="«getLink(WIN_64)»"
								    	class="btn btn-primary btn-large"
								    	style="width: 45%;">Windows 64 Bit</a>&nbsp;
								    	<a href="«getLink(WIN_32)»"
								    	class="btn btn-primary btn-large"
								    	style="width:20%;">32 Bit</a></td>
								    </tr>
								</tbody>
						    </table>
						</div>
				    </div>
					<div class="span1">&nbsp;</div>
				</div>
				    <div class="container">
								<div class="container">
				<h2>Installation Instructions</h2>
				<hr>
				<div class="span1">&nbsp;</div>
				  <div class="span9">
				    <p>
				      <strong>  The JEE6 Generator is implemented in Java, so you must have a
				        <a href="http://www.oracle.com/technetwork/java/index.html">Java
				        Runtime Environment</a> installed in order to proceed.
				      </strong>
				    </p>
				    <p>
				      There are two easy ways to get the JEE6 Generator up and running. A pre-configured Eclipse distribution is available
				      which has already all the necessary plug-ins installed. Alternatively, you can install the JEE6 Generator into your
				      existing Eclipse by means of the Eclipse update mechanism.
				    </p>
				    <!--  section -->
				    <section id="InstallDistro" style="padding-top: 68px; margin-top: -68px;">
				    <h2>  Install Pre-Configured Eclipse
				    </h2>
				      <ol>
				        <li>Download the distribution from above that matches your OS.</li>
				        <li>Unzip the archive into the directory of your choice.
								<strong>  Windows Users should choose a directory close to the root since the zip contains a
								deeply nested folder structure. The maximum path length on windows may not exceed 256 characters.
								</strong>
				        </li>
				        <li>Launch Eclipse and select the workspace location. A workspace location is the directory for your user data
								    and project files.
				        </li>
				      </ol>
				    </section>
				    <!--  section -->
				    <section style="padding-top: 68px; margin-top: -68px;">
				    <h2>  Install the JEE6 Generator from Update Site
				    </h2>
				    <p>
				      If you have an Eclipse running :
				    </p>
				      <ul>
				        <li>
								    Choose
								<strong>  Help -&gt; Install New Software...
								</strong> from the menu bar and
								<strong>  Add...
								</strong> Insert one of the update site URLs from above.
								    This site aggregates all the necessary and optional components and dependencies of the JEE6 Generator.
				        </li>
				        <li>Select the <i>JEE6 DSL</i> from the category <i>JEE6-Generator</i> and
				        complete the wizard by clicking the <i>Next</i> button until you can click <i>Finish</i>.
				        </li>
				        <li>
								    After a quick download and a restart of Eclipse, the JEE6 Generator is ready to use.
				        </li>
				      </ul>
				    </section>
				  </div>
				<div class="span1">&nbsp;</div>
				</div>
				<div class="container">
					<h2>FAQs</h2>
					<hr />
					<div class="span1">&nbsp;</div>
					<div class="span9" id="faq">
						<div class="accordion" id="accordion2">
						    «faqEntry('What is an update site?','''
								Eclipse comes with a built-in update manager, that understands so called update sites.
								When in Eclipse open the <i>"Help"</i> menu and click on <i>"Install new Software..."</i>.
						    ''')»
						    «faqEntry('What is the license of the JEE6 Generator?','''
								<p>The JEE6 Generator is freely available under the <a href="http://www.eclipse.org/legal/epl-v10.html">Eclipse Public License</a>.</p>
								<p>
									The license allows to use the JEE6 Generator for development and even for developing and selling commercial products based on the JEE6 Generator.
								</p>
						    ''')»
						</div>
			  		</div>
					<div class="span1">&nbsp;</div>
				</div>
			</div>
		</div>
	'''

	def faqEntry(String question, CharSequence answer) '''
		«val key = question.replaceAll('\\W','_')»
		<div class="accordion-group">
		  <div class="accordion-heading">
			<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#«key»"">
			  «question»
			</a>
		  </div>
		  <div id="«key»" class="accordion-body collapse">
			<div class="accordion-inner">
			  «answer»
			</div>
		  </div>
		</div>
	'''

}