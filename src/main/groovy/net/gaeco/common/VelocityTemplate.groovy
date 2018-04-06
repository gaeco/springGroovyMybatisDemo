package net.gaeco.common

import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine

class VelocityTemplate {

    private String templatePath
    private VelocityContext context

    public VelocityTemplate(String _templatePath,VelocityContext _context){
        this.templatePath = _templatePath
        this.context = _context
    }

    public String getHtmlStr(){
        Properties p = new Properties()
        p.setProperty( "resource.loader", "file" )
        p.setProperty( "file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")

//        p.setProperty( "file.resource.loader.description", "Velocity File Resource Loader")
//        p.setProperty( "file.resource.loader.path", ".")
//        p.setProperty( "file.resource.loader.cache", "false")
//        p.setProperty( "file.resource.loader.modificationCheckInterval", "2")

        p.setProperty( "input.encoding", "UTF-8")
        p.setProperty( "output.encoding", "UTF-8")

        VelocityEngine ve = new VelocityEngine()
        ve.init(p)

        Template t = ve.getTemplate(this.templatePath)

        StringWriter writer = new StringWriter()

        t.merge( this.context, writer )

        return writer.toString()
    }

}
