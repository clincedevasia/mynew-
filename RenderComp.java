package com.statoil.reinvent.components.wcmuse;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

public class RenderComp extends BaseComponent  {
	private static final Logger LOG = LoggerFactory.getLogger(BaseComponent.class);
	private String pathfield;
	private Date end;
	private Date start;
	protected ValueMap properties;
	protected Resource res;
	
	
		public String getPathfield() {
		return pathfield;
	}

	

		public Date getStart() {
			return start;
		}
		public Date getEnd() {
			return end;
		}
		
		private Date calculateDate(String path) throws ValueFormatException, PathNotFoundException, RepositoryException {
			String val=null;
		 Date startDateTime = null;
			res=getResourceResolver().getResource(path+"/jcr:content/par/editcomp_519997790");
			if (res!= null) {
			Node node=res.adaptTo(Node.class);
			
				if (node.hasProperty("start")) {
			val=node.getProperty("start").getValue().getString();
			try {
				startDateTime = convertToDate(val);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			}
			}
			
			return startDateTime;
		}

		
		private Date convertToDate(String dateTimeString) throws ParseException {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			return dateFormat.parse(dateTimeString);
		} catch (ParseException e) {
			return null;
		}
	}
		@Override
		protected void activated() throws Exception {
			LOG.info("inside");
			this.pathfield=getProperties().get("path", String.class);
			this.start=calculateDate(pathfield);
			
					
		}
	
}
