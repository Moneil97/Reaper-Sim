//package chool_chids;
//
//
//import java.io.File;
//import java.io.InputStream;
//import java.net.URL;
//
//import com.xuggle.mediatool.IMediaReader;
//import com.xuggle.mediatool.MediaListenerAdapter;
//import com.xuggle.mediatool.ToolFactory;
//
///**
// * Using {@link IMediaReader}, takes a media container, finds the first video stream,
// * decodes that stream, and then plays the audio and video.
// *
// * @author aclarke
// * @author trebor
// */
//
//public class DecodeAndPlayAudioAndVideo extends MediaListenerAdapter
//{
//  /**
//   * Takes a media container (file) as the first argument, opens it,
//   * plays audio as quickly as it can, and opens up a Swing window and
//   * displays video frames with <i>roughly</i> the right timing.
//   *  
//   * @param args Must contain one string which represents a filename
//   */
//
//  public static void main(String[] args)
//  {
////    if (args.length <= 0)
////      throw new IllegalArgumentException(
////        "must pass in a filename as the first argument");
//    
//    // create a new mr. decode an play audio and video
////    IMediaReader reader = ToolFactory.makeReader(args[0]);
////	  IMediaReader reader = ToolFactory.makeReader("I:\\Programming\\Workspaces\\AP Workspace\\2nd Quarter\\Reaper Sim\\src\\resources\\test.mp4");
////    reader.addListener(ToolFactory.makeViewer());
////    while(reader.readPacket() == null)
////      do {} while(false);
//	  
//	  new DecodeAndPlayAudioAndVideo();
////    
//  }
//  
//  public DecodeAndPlayAudioAndVideo(){
//	  
//	 //File f = new File("I:\\Programming\\Workspaces\\AP Workspace\\2nd Quarter\\Reaper Sim\\src\\resources\\rick.mp4");
//	  System.out.println("run");
//	  
//	  File f = new File("P:\\Files\\la media\\vid.mp4");
//	 
//	 System.out.println("run");
//	 // System.out.println(f.getAbsolutePath());
//	  
////	  IMediaReader reader = ToolFactory.makeReader("src\\resources\\rick.mp4");
////	  File f = new File("src\\resources\\rick.mp4");
////	  InputStream in = ClassLoader.getSystemResourceAsStream("\\resources\\rick.mp4");
////	  System.out.println(in.toString());
////	  
////	  URL url = this.getClass().getResource("/resources/rick.mp4");
////	  System.out.println(url);
////	  
////	  f = new File(url.getPath());
//	  
//	  //IMediaReader reader = ToolFactory.makeReader(f.getAbsolutePath());
//	  IMediaReader reader = ToolFactory.makeReader(f.getAbsolutePath());
//	    reader.addListener(ToolFactory.makeViewer());
//	    while(reader.readPacket() == null)
//	      do {} while(false);
//	    
//  }
//
//}