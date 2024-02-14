package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SoftwareController implements Initializable{

	@FXML
	private Button close;
	
	@FXML
	private Button maximize;
	
	@FXML
	private Button minimize;
	
	@FXML
	private AnchorPane barSoft; 
	
	@FXML
	private ImageView imgPerfil;
	
	@FXML
	private Text namePsicologo;
	
	private double x = 0;
	private double y = 0;	
	
	private String urlFoto;
	
	File inputFile = new File("usuarios.txt");
	File tempFile = new File("temp.txt");
	
	Image image = new Image(getClass().getResource(foto()).toExternalForm());

	public String foto() {
		 try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))){
			 String word;
             while ((word = reader.readLine()) != null) {
                 String[] linha = word.split("^");
                 for (int i = 0; i <= linha.length - 1; i++) {
                     String[] partes = linha[i].split(",");
                     if(partes[4].equals("-")) {
                    	 urlFoto = partes[3].toString();
                    	 System.out.println(partes[0]);
                     }
                 }
             }
             return urlFoto;
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "/img/profile.jpg";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "/img/profile.jpg";
		}
	}
	
	private void atualizarArquivosTemporarios(String urlFoto) throws IOException {
	    try (BufferedReader reader = new BufferedReader(new FileReader(tempFile));
	         BufferedWriter writer = new BufferedWriter(new FileWriter("tempFileTemp.txt"));
	         BufferedWriter writerU = new BufferedWriter(new FileWriter("usuarioFile.txt"))) {

	        String word;
	        while ((word = reader.readLine()) != null) {
	            String[] linha = word.split("^");
	            for (int i = 0; i <= linha.length - 1; i++) {
	                String[] partes = linha[i].split(",");
	                if (partes.length >= 4 && partes[4].equals("-")) {
	                    writer.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + urlFoto + "," + "-" + "," + "^");
	                    writerU.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + urlFoto + "," + "^");
	                } else {
	                    writer.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + foto() + "," + "x" + "," + "^");
	                    writerU.write(partes[0] + "," + partes[1] + "," + partes[2] + "," + foto() + "," + "^");
	                }
	                writer.newLine();
	                writerU.newLine();
	            }
	        }

	        // Fechar os escritores antes de renomear
	        writer.close();
	        writerU.close();
	        
	        java.io.File userFile = new java.io.File("usuarios.txt");
	        if (userFile.exists()) {
	        	userFile.delete();
	        }
	        
	        // Renomear o arquivo temporário para o original
	        java.io.File userFileTemp = new java.io.File("usuarioFile.txt");
	        userFileTemp.renameTo(userFile);
	        if (userFileTemp.exists()) {
	        	userFileTemp.delete();
	        }
	        
	        java.io.File tempFile = new java.io.File("temp.txt");
	        if (tempFile.exists()) {
	            tempFile.delete();
	        }

	        // Renomear o arquivo temporário para o original
	        java.io.File tempFileTemp = new java.io.File("tempFileTemp.txt");
	        tempFileTemp.renameTo(tempFile);
	        if (tempFileTemp.exists()) {
	        	tempFileTemp.delete();
	        }

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private void copiarImagem(Path origem, String caminhoDestino) throws IOException {
	    Path destino = Path.of(caminhoDestino);

	    // Certifica-se de que a pasta de destino existe, se não, cria
	    if (!Files.exists(destino.getParent())) {
	        Files.createDirectories(destino.getParent());
	    }

	    // Copia a imagem para a pasta de destino
	    Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public void mudarIMG() {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Escolha uma imagem");
	    fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
	    );

	    // Obter o arquivo selecionado
	    Stage stage = new Stage();
	    java.io.File file = fileChooser.showOpenDialog(stage);

	    if (file != null) {
	        try {
	            Path destino = Path.of("src", "img", file.getName());
	            copiarImagem(file.toPath(), destino.toString());

	            urlFoto = "/img/" + file.getName();

	            image = new Image(file.toURI().toString());
		        imgPerfil.setImage(image);
		        
	            System.out.println(urlFoto);

	            atualizarArquivosTemporarios(urlFoto);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	 public void close() {
	        System.exit(0);
	    }
	 
	 public void maximize() {
		  Stage stage = (Stage) barSoft.getScene().getWindow();
		 if(stage.isFullScreen()) {
				stage.setFullScreen(false);
		 }else {
				stage.setFullScreen(true);
		 }
	 }
	 
	 @FXML
    private void onMousePressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) barSoft.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
 
    @FXML
    private void minimizeWindow() {
        Stage stage = (Stage) barSoft.getScene().getWindow();
        stage.setIconified(true);
        
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imgPerfil.setImage(image);
		Circle circle = new Circle(50.0);
		circle.setTranslateX(70.0);
		circle.setTranslateY(57.0);
		imgPerfil.setClip(circle);
		imgPerfil.setPreserveRatio(false);
	
	}

}
