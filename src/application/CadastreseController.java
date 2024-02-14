package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadastreseController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private TextField cadUser;

    @FXML
    private TextField cadCod;

    @FXML
    private PasswordField cadPassword;

    @FXML
    private PasswordField cadPasswordRepeat;

    @FXML
    private Button submit;

    @FXML
    private Text alert;
    
    private double x = 0;
	private double y = 0;

	public void enter() {
		cadUser.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	cadastrarUsuario();
                }
            }
        });
		
		cadCod.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	cadastrarUsuario();
                }
            }
        });
		
		cadPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	cadastrarUsuario();
                }
            }
        });
		
		cadPasswordRepeat.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                	cadastrarUsuario();
                }
            }
        });
    }

    public void close() {
        System.exit(0);
    }

    public void cadastrarUsuario() {
        String arquivo = "usuarios.txt";

        if (cadUser.getText().isEmpty() || cadCod.getText().isEmpty() || cadPassword.getText().isEmpty()
                || cadPasswordRepeat.getText().isEmpty()) {
            alert.setText("Ainda existem campos não preenchidos! Por favor, preencha todos os campos.");
        } else if (cadPassword.getText().equals(cadPasswordRepeat.getText())) {
            if(cadCod.getText().matches("[0-9]+") && cadCod.getText().length() == 6) {
            	try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
                    writer.write(cadUser.getText() + "," + cadCod.getText() + "," + cadPassword.getText() +"," + "/img/profile.jpg" + "," +"^");
                    writer.newLine();
                    System.out.println("Cadastrado");
                    
                    submit.getScene().getWindow().hide();
					Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					
					stage.setTitle("PsiMind");
					stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/icone.png")));
					
					


					root.setOnMousePressed((MouseEvent event) -> {
						x = event.getSceneX();
						y = event.getSceneY();
					});

					root.setOnMouseDragged((MouseEvent event) -> {
						stage.setX(event.getScreenX() - x);
						stage.setY(event.getScreenY() - y);
					});

					stage.initStyle(StageStyle.TRANSPARENT);
					stage.setScene(scene);
					stage.show();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                    alert.setText("Erro ao salvar no arquivo.");
                }
            }else {
            	alert.setText("O campo de Codigo está incorreto! ele deve possuir apenas 6 numeros, sem caracteres!");
            }
        } else {
            alert.setText("As senhas devem ser iguais!!");
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }
}
