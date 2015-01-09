package learn.english.core.test;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;
import java.util.List;

/**
 * Created by yaroslav on 11/29/14.
 */
@ArquillianSuiteDeployment

public class Deployments {
    @Deployment
    public static WebArchive createDeployment() {
        System.out.println("Create core WebArchive for Integration Test");

        List<JavaArchive> libs = Maven.resolver().loadPomFromFile("pom.xml").
                importCompileAndRuntimeDependencies().resolve().withTransitivity().asList(JavaArchive.class);

        for (JavaArchive lib : libs) {
            if(lib.getName().equals("model-1.0-SNAPSHOT.jar")){
                lib.delete("/META-INF/persistence.xml");
                Node node = lib.get("/META-INF/test/persistence.xml");
                lib.add(node.getAsset(), "/META-INF/persistence.xml");
            }
        }

        WebArchive war = ShrinkWrap.create(WebArchive.class).addPackages(true, "learn.english.core");
        for (File file: new File("src/main/resources").listFiles())
            war.addAsResource(file);
        war.addAsLibraries(libs);
        System.out.println(war.toString(true));
        return war;
    }

}
