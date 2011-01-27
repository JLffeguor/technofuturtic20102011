package blackbelt.lucene.spring;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.lucene.ConfigIndex;
import blackbelt.lucene.SectionText;
import blackbelt.lucene.SectionTextDocument;

@Service
@Transactional
public class IndexerService {
	@Autowired
	private SectionDao dao;
	
	public List<SectionText> getLastVersionOfEachSectionTexts(){
		return dao.createQuery("select s1 from SectionText s1 where s1.version=(" +
				"select max(s2.version) from SectionText s2 where s2.sectionid=s1.sectionid and s2.language=s1.language " +
				"group by s2.sectionid)" +
				"order by s1.sectionid");
	}
}
