package generator

import bootstrap.Body
import bootstrap.HtmlExtensions
import bootstrap.PostProcessor
import bootstrap.XdocExtensions
import com.google.inject.Binder
import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Module
import java.io.File
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xdoc.XdocRuntimeModule
import org.eclipse.xtext.xdoc.XdocStandaloneSetup
import org.eclipse.xtext.xdoc.xdoc.AbstractSection
import org.eclipse.xtext.xdoc.xdoc.Document
import org.eclipse.xtext.xdoc.xdoc.ImageRef
import xdocgen.DocumentLoad

import static extension com.google.common.io.Files.*

class Documentation extends AbstractXdocBaseWebsite {
	
	new() {
		doc = docLoader.loadDocument(xdocDocumentRootFolder)
	}

	override getStandaloneSetup() {
		new DocumentationSetup
	}
	
	def getXdocDocumentRootFolder() {
		'../de.itemis.jee6.xdoc/xdoc'
	}

	override path() {
		"documentation.html"
	}

	val Document doc

	@Inject DocumentLoad docLoader
	@Inject extension Body
	@Inject extension HtmlExtensions
	@Inject PostProcessor processor
	
	override website() {
		processor.postProcess(super.website())
	}
	
	override generateTo(File targetDir) {
		super.generateTo(targetDir)
		copyImages(doc, targetDir)
	}

	def copyImages(Document doc, File targetDir) {
		val iter = EcoreUtil::getAllContents(doc.eResource.resourceSet, true)
		iter.filter(typeof(ImageRef)).forEach[
			val source = new File(eResource.URI.trimSegments(1).toFileString, it.path)
			if (!source.exists)
				throw new IllegalStateException("Referenced Image "+source.canonicalPath+" does not exist in "+eResource.URI.lastSegment+" line "+NodeModelUtils::getNode(it).startLine)
			val target = new File(targetDir, it.path)
			println(target.canonicalPath)
			
			source.newInputStreamSupplier.copy(target)
		]
	}

	override contents() '''
		<div id="main" role="main">
			<div id="maincontainer" class="clearfix centering">
				<h2>Dokumentation</h2>
				<div class="box introduction">
					<div id="dokumentation_toc" class="container">
						«doc.menu»
					</div>
					<div id="dokumentation_content">  
						«doc.body»
					</div>
				</div>
			</div>
		</div>
	'''
	
	def menu(Document doc) '''
		<ul id="nav-outline">
			«FOR chapter : doc.chapters»
				<li><a href="«chapter.href»">«chapter.title.toHtmlText»</a></li>
				«FOR section : chapter.subSections BEFORE '<ul>' AFTER '</ul>'»
					<li><a href="«section.href»">«section.title.toHtmlText»</a></li>
				«ENDFOR»
				</li>
			«ENDFOR»
			«FOR part : doc.parts»
				«FOR chapter : part.chapters»
					<li><a href="«chapter.href»">«chapter.title.toHtmlText»</a>
					«FOR section : chapter.subSections BEFORE '<ul>' AFTER '</ul>'»
						<li><a href="«section.href»">«section.title.toHtmlText»</a></li>
					«ENDFOR»
					</li>
				«ENDFOR»
			«ENDFOR»
			«additionalLinks»
		</ul>
	'''

	def additionalLinks() '''
		<li><strong>Additional Resources</strong></li>
		<li>
			<ul>
				<li><a href="javadoc/jee6">API Documentation Java EE 6 (JavaDoc)</a></li>
				<li><a href="javadoc/jee7">API Documentation Java EE 7 (JavaDoc)</a></li>
			</ul>
		</li>
	'''
	
	override protected getDocument() {
		doc
	}	
}

class DocumentationSetup extends XdocStandaloneSetup implements Module {
	override createInjector() {
		val module = new XdocRuntimeModule
		Guice::createInjector(module, this)
	}
	
	override configure(Binder binder) {
		binder.bind(typeof(Body)).to(typeof(DocumentationBody))
	}
}

class DocumentationBody extends Body {
	@Inject extension XdocExtensions
	@Inject extension HtmlExtensions

	override h1(AbstractSection chapter) '''
		<section id="«chapter.hrefId»">
			<h2>
				«chapter.title.toHtmlText»
			</h2>
			«FOR content : chapter.contents»
				«content?.toHtmlParagraph»
			«ENDFOR»
			«FOR section: chapter.sections»
				«section.h2»
			«ENDFOR»
			«topButton»
		</section>
	'''

	override h2(AbstractSection section) '''
		<section id="«section.hrefId»">
			<h3>«section.title.toHtmlText»</h3>
			«FOR content : section.contents»
				«content.toHtmlParagraph»
			«ENDFOR»
			«FOR subsection: section.sections»
				«subsection.h3plus(4)»
			«ENDFOR»
			«topButton»
		</section>
	'''
	
	def topButton() '''
		<div class="button">
			<a href="#maincontainer">top</a>
		</div>
	'''
}