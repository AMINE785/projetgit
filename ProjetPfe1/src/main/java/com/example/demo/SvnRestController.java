package com.example.demo;



import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;
import org.sonarqube.ws.Issues.Issue;
import org.sonarqube.ws.client.HttpConnector;
import org.sonarqube.ws.client.HttpConnector.Builder;
import org.sonarqube.ws.client.WsClient;
import org.sonarqube.ws.client.WsClientFactories;
import org.sonarqube.ws.client.issue.SearchWsRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueReference;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;




@CrossOrigin(origins="*")
@RestController
@RequestMapping("/")

public class SvnRestController {
	
	
	@RequestMapping(value="/svn",method=RequestMethod.GET)
	public Object getResult() throws SVNException 
	{     	DAVRepositoryFactory.setup( );

    String url = "https://subversion.assembla.com/svn/amine^amine-first-space/trunk/projetpfe";
    
    long startRevision = 0;
    long endRevision = -1; //HEAD (the latest) revision

    SVNRepository repository = null;
    
        repository = SVNRepositoryFactory.create( SVNURL.parseURIEncoded( url ) );
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager( "amine789", "Amine1994" );
        repository.setAuthenticationManager( authManager );
        Collection logEntries = null;
        
        logEntries = repository.log( new String[] { "" } , null , startRevision , endRevision , true , true );

    List<com.example.demo.Result> resu = new ArrayList<com.example.demo.Result>();	  
  
	for ( Iterator entries = logEntries.iterator( ); entries.hasNext( ); ) {
        SVNLogEntry logEntry = ( SVNLogEntry ) entries.next( );
        resu.add(new com.example.demo.Result(logEntry.getRevision(),logEntry.getAuthor(), logEntry.getDate()
        		,logEntry.getMessage()));		
        if (resu.size()==8) {
        	return resu ;}

         
    }
	
	return resu ;
	
	
}
	
@RequestMapping(value="/he",method=RequestMethod.GET)
public String jira() {
	

	String auth = new String(Base64.encode("mohamedamine.ouertatani@ensi-uma.tn:30101994Amine"));
	Client client = Client.create();
	WebResource webResource = client.resource("https://amine789.atlassian.net/rest/api/2/project");
	ClientResponse response = webResource
			.header("Authorization","Basic "+ auth)
	    .type("application/json")
	    .accept("application/json")
	    .get(ClientResponse.class);
	String res = response.getEntity(String.class);
return res;}
@RequestMapping(value="/jenkins",method=RequestMethod.GET)
public Jenkinsresult jenkins() throws URISyntaxException, IOException {
	JenkinsServer jenkins = new JenkinsServer(new URI("http://localhost:9090/"), "amine789", "30101994Amine");
	Map<String, Job> jobs = jenkins.getJobs();
	JobWithDetails job = jobs.get("pfeprojet").details();
	
	List<Build> builds= job.getAllBuilds();
	String b =builds.toString() ;
	 Jenkinsresult jesre = new Jenkinsresult();
	 jesre.setName(job.getName());
	 jesre.setBuild(b);
	 return jesre;
	}
@RequestMapping(value="/sonar",method=RequestMethod.GET)
public String sonar()  {
	
	
	
	
	
	 Builder builder = HttpConnector.newBuilder();

	    builder.url("http://127.0.0.1:9000/sonar/");

	    builder.connectTimeoutMilliseconds(10000);

	    HttpConnector httpConnector = builder.build();

	    SearchWsRequest searchWsRequest = new org.sonarqube.ws.client.issue.SearchWsRequest();

	    List<String> projectKeys = new ArrayList<String>(1);

	    projectKeys.add("");

	    searchWsRequest.setProjectKeys(projectKeys);

	    final WsClient wsClient = WsClientFactories.getDefault().newClient(httpConnector);
	    List<Issue> issues = wsClient.issues().search(searchWsRequest).getIssuesList();
	    
	     return issues.toString() ;
}

@RequestMapping(value="/sonarpfe",method=RequestMethod.GET)
public String sonarpfe () throws IOException {
	
	HttpGet httpGet = new HttpGet("http://127.0.0.1:9000/sonar/api/measures/component?componentKey=java-sonar-runner-simple&metricKeys=ncloc,coverage,bugs,vulnerabilities");
    try(CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpGet);) {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }
}
	
}







