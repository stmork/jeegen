package generator

class Download extends AbstractWebsite {

	override path() {
		"download.html"
	}

	def String getKeplerLink(String platform) {
		'distro/eclipse-jee-generator-kepler-' + platform + platform.fileExtension
	}
	
	def String getLunaLink(String platform) {
		'distro/eclipse-jee-generator-luna-' + platform + platform.fileExtension
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
				«downloadBoxes»
			</div>
			<div class="container">
				<h2>Installation Instructions</h2>
	    		<p>
	      			<strong>Die JEE-Generatoren sind in Java implementiert, daher muss die
	        		<a href="http://www.oracle.com/technetwork/java/index.html">Java
	        		Runtime Environment</a> installiert sein.
	      			</strong>
	    		</p>
				<p>
					Es gibt zwei einfache Wege, die JEE-Generatoren ans Laufen zu bringen. Eine vorkonfigurierte Eclipse-Distribution
					ist bereits mit allen notwendigen Plugins vorinstalliert verfügbar. Alternativ kann über eine Update Site
					ein Eclipse mit allen notwendigen Plugins nachinstalliert werden.
				</p>
				<section>
					<h2>  Installiere vorkonfiguriertes Eclipse</h2>
					<ol>
						<li>Download die Distribution zum passenden Betriebssystem.</li>
						<li>
							Das Archiv muss im Verzeichnis der Wahl ausgepackt werden
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
				<section>
					<h2>JEE-Generatoren von der Update Site installieren  </h2>
					<p>
						Wenn Eclipse läuft:
						<ul>
							<li>
								Wähle <strong>Help -&gt; Install New Software...</strong> aus der Menüleiste und
								<strong>  Add...</strong> Geben Sie eine der Update Site URLs von oben an.
								Diese Site fasst alle benötigten Komponenten und Abhängigkeiten für die JEE-Generatoren zusammen.
							</li>
							<li>Wähle den passenden <i>JEE-Generator</i> aus der Kategorie <i>JEE-Generators</i> und durch wiederholtes Klicken von
								<i>Next</i> und einem abschließenden <i>Finish</i> wird die Installation abgeschlossen.
							</li>
							<li>
								Nach einem kurzen Download stehen die JEE-Generatoren zum Gebrauch zur Verfügung.
							</li>
						</ul>
					</p>
				</section>
			</div>
			<div class="container">
				<h2>FAQs</h2>
				<div>
					<h3>Was ist eine Updatesite?</h3>
					<p>
						Eclipse hat einen eingebauten Updatemanager, dieser arbeitet mit so genannten Updatesites als Quelle.<br />
						In Eclipse öffne das <i>"Help"</i> Menü und klicke auf <i>"Install new Software..."</i>.
					</p>
				    <h3>Was ist die Lizenz der JEE-Generatoren?</h3>
					<p>
						Die JEE-Generator sind frei verfügbar unter der <a href="http://www.eclipse.org/legal/epl-v10.html">Eclipse Public License</a>.
					</p>
				</div>
			</div>
		</div>
	</div>
	'''
	
	def downloadBoxes() '''
	<div class="clearfix">
		<div class="downloadbox left">
			<table>
		    	<thead>
					<tr>
						<th class="focus"><h2>Update Sites</h2> <span>Nutze den Eclipse Update Manager</span></th>
					</tr>
		    	</thead>
				<tfoot><tr><td></td></tr></tfoot>
				<tbody>
					<tr>
						<td style="text-align: center;">
							<a href="http://jee-generator.de/updates/kepler/" class="btn" style="width: 70%;">Kepler (Release)</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<a href="http://jee-generator.de/updates/luna/" class="btn" style="width: 70%;">Luna (Release)</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<a href="http://jee-generator.de/updates/snapshot/kepler" class="btn" style="width: 70%;">Kepler (Snapshot)</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<a href="http://jee-generator.de/updates/snapshot/luna" class="btn" style="width: 70%;">Luna (Snapshot)</a>
						</td>
					</tr>
				</tbody>
		    </table>
		</div>
		<div class="downloadbox right">
		    <table>
		    	<thead>
					<tr>
						<th class="focus"><h2>Full Eclipse (Kepler)</h2> <span>Einfach runterladen und entpacken</span></th>
					</tr>
		    	</thead>
				<tfoot><tr><td></td></tr></tfoot>
				<tbody>
					<tr>
						<td style="text-align: center; white-space: nowrap;">
							<a href="«getKeplerLink(OSX_64)»"
								class="btn" style="width: 45%;">OS X 64 Bit</a>&nbsp;
							<a href="«getKeplerLink(OSX_32)»"
								class="btn" style="width:20%;">32 Bit</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center; white-space: nowrap;">
							<a href="«getKeplerLink(LINUX_64)»"
								class="btn" style="width: 45%;">Linux 64 Bit</a>&nbsp;
							<a href="«getKeplerLink(LINUX_32)»"
								class="btn" style="width:20%;">32 Bit</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center; white-space: nowrap;">
							<a href="«getKeplerLink(WIN_64)»"
								class="btn" style="width: 45%;">Windows 64 Bit</a>&nbsp;
							<a href="«getKeplerLink(WIN_32)»"
								class="btn" style="width:20%;">32 Bit</a>
						</td>
					</tr>
				</tbody>
		    </table>
		</div>
		<div class="downloadbox right">
		    <table>
		    	<thead>
					<tr>
						<th class="focus"><h2>Full Eclipse (Luna)</h2> <span>Einfach runterladen und entpacken</span></th>
					</tr>
		    	</thead>
				<tfoot><tr><td></td></tr></tfoot>
				<tbody>
					<tr>
						<td style="text-align: center; white-space: nowrap;">
							<a href="«getLunaLink(OSX_64)»"
								class="btn" style="width: 45%;">OS X 64 Bit</a>&nbsp;
							<a href="«getLunaLink(OSX_32)»"
								class="btn" style="width:20%;">32 Bit</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center; white-space: nowrap;">
							<a href="«getLunaLink(LINUX_64)»"
								class="btn" style="width: 45%;">Linux 64 Bit</a>&nbsp;
							<a href="«getLunaLink(LINUX_32)»"
								class="btn" style="width:20%;">32 Bit</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center; white-space: nowrap;">
							<a href="«getLunaLink(WIN_64)»"
								class="btn" style="width: 45%;">Windows 64 Bit</a>&nbsp;
							<a href="«getLunaLink(WIN_32)»"
								class="btn" style="width:20%;">32 Bit</a>
						</td>
					</tr>
				</tbody>
		    </table>
		</div>
	</div>
	'''
}