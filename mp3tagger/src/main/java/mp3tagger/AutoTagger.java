package mp3tagger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class AutoTagger {
	
	
	private String dirPath="";
	boolean validPath=false;
	private ArrayList<Path> foundMP3Files = new ArrayList<Path>();
	
	public AutoTagger(String dirPath) {
		this.dirPath=dirPath;
		findMP3Files(this.dirPath);
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Please specify a target directory");
		Scanner in = new Scanner(System.in);
		
		while (in.hasNextLine()) {
			
			AutoTagger mp3tagger = new AutoTagger(in.nextLine());
			
			mp3tagger.autoTagFiles();
		}
	}
	
	
	private void findMP3Files(String pathString) {
		
		Path path = Path.of(pathString);
		try (DirectoryStream<Path> entries = Files.newDirectoryStream(path, "*.mp3")){
			
			entries.forEach(foundMP3Files::add); //add all mp3 files to the list
			
		} catch (IOException e) {
			
			validPath=false;
		}
	}
	
	public void autoTagFiles() {
		
		for(Path path : foundMP3Files) {
			
			try {
				Mp3File mp3File = new Mp3File(path.toString());
				System.out.println("Process File: " + mp3File.getFilename());

			} catch (UnsupportedTagException | InvalidDataException | IOException e) {
				
				System.out.println("Unable to open file: " + path.getFileName());
			}
			
		}
		
	}
}
