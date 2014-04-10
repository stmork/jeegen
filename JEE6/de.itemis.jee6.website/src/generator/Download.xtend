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
	<div id="main" role="main">
		<div id="maincontainer" class="clearfix centering">
			<h2>Downloads</h2>
			<div class="box introduction">
					<!--table one starts-->
					<div class="span4">
						<div class="clearfix">
							<div class="pricing left">
								<table>
									<tfoot>
										<tr>
											<td></td>
										</tr>
									</tfoot>
									<tbody>
									<tr>
										<td class="focus">Update Sites <span>Nutze den Eclipse Update Manager</span></td>
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
										  href="http://jee6-generator.itemis.de/updates/snapshot/"
										class="has-popover btn btn-primary btn-large"
										style="width: 70%;">Snapshot</a></td>
									</tr>
								</tbody>
							    </table>
							</div>
						    <div class="span4">
								<div class="pricing right">
								    <table>
										<tfoot>
											<tr>
												<td></td>
											</tr>
										</tfoot>
										<tbody>
											<tr>
												<td class="focus">Full Eclipse <span>Einfach runterladen und entpacken</span></td>
											</tr>
											<tr>
												<td style="text-align: center; white-space: nowrap;">
													<a href="«getLink(OSX_64)»"
														class="btn btn-primary btn-large"
														style="width: 45%;">OS X 64 Bit</a>&nbsp;
													<a href="«getLink(OSX_32)»"
														class="btn btn-primary btn-large"
														style="width:20%;">32 Bit</a>
												</td>
											</tr>
											<tr>
												<td style="text-align: center; white-space: nowrap;">
													<a href="«getLink(LINUX_64)»"
														class="btn btn-primary btn-large"
														style="width: 45%;">Linux 64 Bit</a>&nbsp;
													<a href="«getLink(LINUX_32)»"
														class="btn btn-primary btn-large"
														style="width:20%;">32 Bit</a>
												</td>
											</tr>
											<tr>
												<td style="text-align: center; white-space: nowrap;">
													<a href="«getLink(WIN_64)»"
														class="btn btn-primary btn-large"
														style="width: 45%;">Windows 64 Bit</a>&nbsp;
													<a href="«getLink(WIN_32)»"
														class="btn btn-primary btn-large"
														style="width:20%;">32 Bit</a>
												</td>
											</tr>
										</tbody>
								    </table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="container">
					<div class="container">
						<h2>Installation Instructions</h2>
				  		<div class="span9">
				    		<p>
				      			<strong>  Der JEE-Generator ist in Java implementiert, daher muss die
				        		<a href="http://www.oracle.com/technetwork/java/index.html">Java
				        		Runtime Environment</a> installiert sein.
				      			</strong>
				    		</p>
							<p>
								Es gibt zwei einfache Wege, den JEE-Generator ans Laufen zu bringen. Eine vorkonfigurierte Eclipse-Distribution
								ist bereits mit allen notwendigen Plugins vorinstalliert verfügbar. Alternativ kann über eine Update Site
								ein Eclipse mit allen notwendigen Plugins nachinstalliert werden.
							</p>
							<!--  section -->
					<section id="InstallDistro" style="padding-top: 68px; margin-top: -68px;">
						<h2>  Installiere vorkonfiguriertes Eclipse</h2>
						<ol>
							<li>Download die Distribution zum passenden Betriebssystem.</li>
							<li>Das Archiv muss im Verzeichnis der Wahl ausgepackt werden
								<strong>Windows Benutzer sollten ein Verzeichnis möglichst in der Nähe des Wurzelverzeichnisses wählen,
								da Windows nur komplette Pfade mit maximal 256 Zeichen akzeptiert und Eclipse eine tiefe
								Verzeichnisstruktur benutzt.
								</strong>
							</li>
							<li>
								Danach kann Eclipse gestartet werden und der gewünschte Workspace ausgewählt werden. Ein Workspace
								ist das Verzeichnis für die Benutzerdaten und Projektdateien.
							</li>
						</ol>
					</section>
					<!--  section -->
					<section style="padding-top: 68px; margin-top: -68px;">
						<h2>  JEE-Generator von der Update Site installieren  </h2>
						<p>
							Wenn Eclipse läuft:
						</p>
						<ul>
							<li>
								Wähle
								<strong>  Help -&gt; Install New Software...
								</strong> aus der Menüleiste und
								<strong>  Add...
								</strong> Geben Sie eine der Update Site URLs von oben an.
								Diese Site fasst alle benötigten Komponenten und Abhängigkeiten für den JEE-Generator zusammen.
							</li>
							<li>Wähle <i>JEE-Generator</i> aus der Kategorie <i>JEE-Generator</i> und durch wiederholtes Klicken von
								<i>Next</i> und einem abschließenden <i>Finish</i> wird die Installation abgeschlossen.
							</li>
							<li>
								Nach einem kurzen Download steht der JEE-Generator zum Gebrauch zur Verfügung.
							</li>
						</ul>
					</section>
					</div>
				</div>
				<div class="container">
					<h2>FAQs</h2>
					<div class="span9" id="faq">
						<div class="accordion" id="accordion2">
						    <h3>Was ist eine Updatesite?</h3>
							<p>
								Eclipse hat einen eingebauten Updatemanager, dieser arbeitet mit so genannten Updatesites als Quelle.<br />
								In Eclipse öffne das <i>"Help"</i> Menü und klicke auf <i>"Install new Software..."</i>.
							</p>
						    <h3>Was ist die Lizenz des JEE-Generators?</h3>
							<p>
								Der JEE-Generator ist frei verfügbar unter der <a href="http://www.eclipse.org/legal/epl-v10.html">Eclipse Public License</a>.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	'''
}