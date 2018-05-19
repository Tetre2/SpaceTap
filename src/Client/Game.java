package Client;

import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application{
	public static double WORLD_WIDTH = 1200;
	public static double WORLD_HEIGHT = 800;

	private static Stage pStage;
	private int timer = 0;

	private Group root = new Group();
	private Scene scene = new Scene(root, WORLD_WIDTH, WORLD_HEIGHT);

	private Group startingScreenGroup = new Group();
	private Scene startingScreen = new Scene(startingScreenGroup, WORLD_WIDTH, WORLD_HEIGHT);	

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("SpaceTap");
		setPrimaryStage(primaryStage);
		
		
		setup();


		primaryStage.show();	


	}

	public static void main(String[] args) {
		launch(args);
	}


	private void game(){

		
		AnimationTimer at = new AnimationTimer() {

			long test = 0;
			
			@Override
			public void handle(long now) {
				if(now - test >= 1_000){
					test = now;
					timer++;
					
				}
			}
		};
		
		at.start();

		
		
		scene.setOnKeyPressed(event->{
			
			if(KeyCode.SPACE == event.getCode()){
				at.stop();
				double d = timer/100.0;
				System.out.println(d);
				Text t = new Text("Your time was: " + d);
				t.setFont(Font.font ("Arial", 20));
				t.setTranslateX(WORLD_WIDTH/2 - t.getLayoutBounds().getWidth()/2);
				t.setTranslateY(WORLD_HEIGHT/4);
				root.getChildren().add(t);
				
			}
			
		});
		
	}




	private void setup(){
		Text t = new Text("Press SPACE after 3 sek");
		t.setFont(Font.font ("Arial", 20));
		t.setTranslateX(WORLD_WIDTH/2 - t.getLayoutBounds().getWidth()/2);
		t.setTranslateY(WORLD_HEIGHT/4);


		Group g = createTextBtn("  Ready  ", null);
		g.setTranslateX(WORLD_WIDTH/2 - g.getLayoutBounds().getWidth()/2);
		g.setTranslateY(WORLD_HEIGHT/2);
		
		g.setOnMouseClicked(event->{
			game();
			pStage.setScene(scene);
			
		});


		startingScreenGroup.getChildren().add(g);
		startingScreenGroup.getChildren().add(t);
		pStage.setScene(startingScreen);	
	}


	public static Stage getPrimaryStage() {
		return pStage;
	}

	private void setPrimaryStage(Stage pStage) {
		Game.pStage = pStage;
	}


	public static Group createTextBtn(String s, Font f){


		Text t = new Text(s);

		if(f == null){
			t.setFont(Font.font ("Arial", 20));
		}else{
			t.setFont(f);
		}

		double textWidth = t.getLayoutBounds().getWidth();
		double textHight = t.getLayoutBounds().getHeight();

		Text x = new Text("x");
		x.setFont(f);
		double wordSpacing = x.getLayoutBounds().getWidth();

		Rectangle r = new Rectangle(textWidth+ wordSpacing*2, textHight+ textHight/2);
		r.setArcHeight(textHight/3);
		r.setArcWidth(textHight/3);
		r.setStrokeWidth(textHight/15);
		r.setStroke(Color.BLACK);
		r.setFill(Color.rgb(204, 204, 255));


		t.setTranslateX(wordSpacing);
		t.setTranslateY(textHight + textHight/8);

		Group g = new Group();
		g.getChildren().addAll(r,t);



		g.setOnMouseEntered(event->{

			r.setFill(Color.rgb(255, 153, 102));

		});

		g.setOnMouseExited(event->{

			r.setFill(Color.rgb(204, 204, 255));

		});


		return g;

	}

}
