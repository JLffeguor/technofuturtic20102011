package blackbelt.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;

public class LuceneTest{

   public static void main(String[] args) {
      // Construct a RAMDirectory to hold the in-memory representation
      // of the index.
      RAMDirectory idx = new RAMDirectory();

      try {
         // Make an writer to create the index
         IndexWriter writer =
                 new IndexWriter(idx,
                         new StandardAnalyzer(Version.LUCENE_30),
                         IndexWriter.MaxFieldLength.LIMITED);

         // Add some Document objects containing quotes
         writer.addDocument(createDocument("Theodore Roosevelt",
                 "It behooves every man to remember that the work of the " +
                         "critic, is of altogether secondary importance, and that, " +
                         "in the end, progress is accomplished by the man who does " +
                         "things."));
         writer.addDocument(createDocument("Friedrich Hayek",
                 "The case for individual freedom rests largely on the " +
                         "recognition of the inevitable and universal ignorance " +
                         "of all of us concerning a great many of the factors on " +
                         "which the achievements of our ends and welfare depend."));
         writer.addDocument(createDocument("Ayn Rand",
                 "There is nothing to take a man's freedom away from " +
                         "him, save other men. To be free, a man must be free " +
                         "of his brothers."));
         writer.addDocument(createDocument("Mohandas Gandhi",
                 "Freedom is not worth having if it does not connote " +
                         "freedom to err."));

         // Optimize and close the writer to finish building the index
         writer.optimize();
         writer.close();

         // Build an IndexSearcher using the in-memory index
         Searcher searcher = new IndexSearcher(idx);

         // Run some queries
         search(searcher, "freedom");
         search(searcher, "free");
         search(searcher, "progress or achievements");

         searcher.close();
      }
      catch (IOException ioe) {
         // In this example we aren't really doing an I/O, so this
         // exception should never actually be thrown.
         ioe.printStackTrace();
      }
      catch (ParseException pe) {
         pe.printStackTrace();
      }
   }

   /**
    * Make a Document object with an un-indexed title field and an
    * indexed content field.
    */
   private static Document createDocument(String title, String content) {
      Document doc = new Document();

      // Add the title as an unindexed field...

      doc.add(new Field("title", title, Field.Store.YES, Field.Index.NO));

      // ...and the content as an indexed field. Note that indexed
      // Text fields are constructed using a Reader. Lucene can read
      // and index very large chunks of text, without storing the
      // entire content verbatim in the index. In this example we
      // can just wrap the content string in a StringReader.
      doc.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED));

      return doc;
   }

   /**
    * Searches for the given string in the "content" field
    */
   private static void search(Searcher searcher, String queryString)
           throws ParseException, IOException {

      // Build a Query object
      QueryParser parser = new QueryParser(Version.LUCENE_30,"content",new StandardAnalyzer(Version.LUCENE_30));
      Query query = parser.parse(queryString);

      int hitsPerPage = 10;
      // Search for the query
      TopScoreDocCollector collector = TopScoreDocCollector.create(5 * hitsPerPage, false);
      searcher.search(query, collector);

      ScoreDoc[] hits = collector.topDocs().scoreDocs;

      int hitCount = collector.getTotalHits();
      System.out.println(hitCount + " total matching documents");

      // Examine the Hits object to see if there were any matches

      if (hitCount == 0) {
         System.out.println("No matches were found for \"" + queryString + "\"");
      } else {
         System.out.println("Hits for \"" + queryString + "\" were found in quotes by:");

         // Iterate over the Documents in the Hits object
         for (int i = 0; i < hitCount; i++) {
            ScoreDoc scoreDoc = hits[i];
            int docId = scoreDoc.doc;
            float docScore = scoreDoc.score;
            System.out.println("docId: " + docId + "\t" + "docScore: " + docScore);

            Document doc = searcher.doc(docId);

            // Print the value that we stored in the "title" field. Note
            // that this Field was not indexed, but (unlike the
            // "contents" field) was stored verbatim and can be
            // retrieved.
            System.out.println("  " + (i + 1) + ". " + doc.get("title"));
            System.out.println("Content: " + doc.get("content"));
         }
      }
      System.out.println();
   }
}
