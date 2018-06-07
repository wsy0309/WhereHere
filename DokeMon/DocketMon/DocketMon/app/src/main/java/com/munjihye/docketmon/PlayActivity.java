package com.munjihye.docketmon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {

    private int stage_cnt = 0;
    private int cur_input = 0;

    private ArrayList<Integer> num = new ArrayList<>();
    private ArrayList<Integer> random_num = new ArrayList<>();
    private ArrayList<Integer> submit_num = new ArrayList<>();

    private FindImage findImage = new FindImage();

    private Timer timer;
    private int timer_cnt = 0;

    private ImageView stage;

    private ImageView board_1;
    private ImageView board_2;
    private ImageView board_3;
    private ImageView board_4;
    private ImageView board_5;
    private ImageView board_6;
    private ImageView board_7;
    private ImageView board_8;
    private ImageView board_9;
    private ImageView board_10;
    private ImageView board_11;
    private ImageView board_12;
    private ImageView board_13;
    private ImageView board_14;
    private ImageView board_15;
    private ImageView board_16;


    private ImageView input_bg;
    private ImageView input_1;
    private ImageView input_2;
    private ImageView input_3;
    private ImageView input_4;
    private ImageView input_5;
    private ImageView input_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        stage = (ImageView)findViewById(R.id.stage);

        board_1 = (ImageView)findViewById(R.id.board_1);
        board_2 = (ImageView)findViewById(R.id.board_2);
        board_3 = (ImageView)findViewById(R.id.board_3);
        board_4 = (ImageView)findViewById(R.id.board_4);
        board_5 = (ImageView)findViewById(R.id.board_5);
        board_6 = (ImageView)findViewById(R.id.board_6);
        board_7 = (ImageView)findViewById(R.id.board_7);
        board_8 = (ImageView)findViewById(R.id.board_8);
        board_9 = (ImageView)findViewById(R.id.board_9);
        board_10 = (ImageView)findViewById(R.id.board_10);
        board_11 = (ImageView)findViewById(R.id.board_11);
        board_12 = (ImageView)findViewById(R.id.board_12);
        board_13 = (ImageView)findViewById(R.id.board_13);
        board_14 = (ImageView)findViewById(R.id.board_14);
        board_15 = (ImageView)findViewById(R.id.board_15);
        board_16 = (ImageView)findViewById(R.id.board_16);

        ImageView start = (ImageView)findViewById(R.id.start_btn);
        final ImageView submit = (ImageView)findViewById(R.id.submit_btn);

        input_bg = (ImageView)findViewById(R.id.input_bg);
        input_1 = (ImageView)findViewById(R.id.input_1);
        input_2 = (ImageView)findViewById(R.id.input_2);
        input_3 = (ImageView)findViewById(R.id.input_3);
        input_4 = (ImageView)findViewById(R.id.input_4);
        input_5 = (ImageView)findViewById(R.id.input_5);
        input_6 = (ImageView)findViewById(R.id.input_6);


        //해당 board num을 제출 배열에 따로 저장해둬서 submit버튼 누르면 정답배열이랑 비교해야함
        board_1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(1);
                setInputImg(cur_input,R.drawable.board_1);}});

        board_2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(2);
                setInputImg(cur_input,R.drawable.board_2);}});
        board_3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(3);
                setInputImg(cur_input,R.drawable.board_3);}
        });
        board_4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(4);
                setInputImg(cur_input,R.drawable.board_4);}
        });
        board_5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(5);
                setInputImg(cur_input,R.drawable.board_5);}
        });
        board_6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(6);
                setInputImg(cur_input,R.drawable.board_6);}
        });
        board_7.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(7);
                setInputImg(cur_input,R.drawable.board_7);}
        });
        board_8.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(8);
                setInputImg(cur_input,R.drawable.board_8);}
        });
        board_9.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(9);
                setInputImg(cur_input,R.drawable.board_9);}
        });
        board_10.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(10);
                setInputImg(cur_input,R.drawable.board_10);}
        });
        board_11.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(11);
                setInputImg(cur_input,R.drawable.board_11);}
        });
        board_12.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(12);
                setInputImg(cur_input,R.drawable.board_12);}
        });
        board_13.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(13);
                setInputImg(cur_input,R.drawable.board_13);}
        });
        board_14.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(14);
                setInputImg(cur_input,R.drawable.board_14);}
        });
        board_15.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(15);
                setInputImg(cur_input,R.drawable.board_15);}
        });
        board_16.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num.add(16);
                setInputImg(cur_input,R.drawable.board_16);}
        });

        for(int i=0; i< 16; i++) {
            num.add(i + 1);
        }

        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //일정 시간마다 랜덤수 발생 & board 배경이미지로 초기화
                        //랜덤수는 정답배열에 따로 저장해둠
                        //랜덤수에 따라 해당하는 board 이미지 변환
                        //board 초기화
                        for(int i= 1; i <= 16 ; i++){
                            setBoard_image(i,findImage.selectBoard_init_img(i));
                        }
                        //input 초기화
                        input_bg.setImageResource(R.drawable.input_bg);
                        for(int i= 0; i < 6 ; i++){
                            setInputImg(i,R.drawable.input_init);
                        }

                        //random submit 클리어
                        Collections.shuffle(num);
                        random_num.clear();
                        submit_num.clear();
                        cur_input = 0;

                        playGame(findStage(stage_cnt));

                        if(stage_cnt == 3){
                            stage.setImageResource(R.drawable.stage_2);
                        }else if(stage_cnt == 6){
                            stage.setImageResource(R.drawable.stage_3);
                        }else if(stage_cnt == 9){
                            // 종료
                        }
                    }
                }
        );

        submit.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //제출배열이랑 정답배열이랑 비교
                        //정답 여부에 따라 input_bg 이미지 변환
                        //board에 정답 띄우기
                        //???그럼 또 스위치?????
                        if (random_num.equals(submit_num)){
                            correct();
                        }
                        else{
                            error();
                        }
                    }
                }
        );
    }

    public void error(){
        input_bg.setImageResource(R.drawable.input_error);

        final TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {

                //이전에 포켓몬 뜬거 없애기
                if (timer_cnt > 0){
                    setBoard_image(random_num.get(timer_cnt-1), findImage.selectBoard_init_img(random_num.get(timer_cnt-1)));
                }
                //타이머 종료 조건
                if (timer_cnt >= findStage(stage_cnt)){
                    //정답 띄우기
                    for (int i = 0; i < random_num.size();i++){
                        setBoard_image(random_num.get(i),findImage.selectBoard_img(random_num.get(i)));
                    }
                    timer_cnt = 0;
                    timer.cancel();
                }
                else{
                    //타이머가 실행될때마다 하는 일
                    int board_img = findImage.selectBoard_img(random_num.get(timer_cnt));
                    //int board_init_img = findImage.selectBoard_init_img(random_num.get(timer_cnt));
                    setBoard_image(random_num.get(timer_cnt), findImage.selectBoard_img(random_num.get(timer_cnt)));
                    timer_cnt++;
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask2,1000,1000);



    }

    public void correct(){
        input_bg.setImageResource(R.drawable.input_correct);
        stage_cnt++;
    }


    public int findStage(int stage_cnt){
        int stage = 0;
        if(stage_cnt < 3){
            stage = 4;
        }else if(stage_cnt >=3 && stage_cnt <6){
            stage = 5;
        }else if(stage_cnt >=6 && stage_cnt <9){
            stage = 6;
        }else if(stage_cnt == 9){
            stage = 0;
        }
        return stage;
    }


    public void playGame(final int count) {

        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                //이전에 포켓몬 뜬거 없애기
                if (timer_cnt > 0){
                    setBoard_image(random_num.get(timer_cnt-1), findImage.selectBoard_init_img(random_num.get(timer_cnt-1)));
                }
                //타이머 종료 조건
                if (timer_cnt >= count){
                    timer_cnt = 0;
                    timer.cancel();
                }
                else{

                    //타이머가 실행될때마다 하는 일
                    //Random random = new Random();
                    //random_num.add(timer_cnt,random.nextInt(16) + 1);
                    random_num.add(timer_cnt,num.get(timer_cnt));
                    int board_img = findImage.selectBoard_img(random_num.get(timer_cnt));
                    //int board_init_img = findImage.selectBoard_init_img(random_num.get(timer_cnt));
                    setBoard_image(random_num.get(timer_cnt), findImage.selectBoard_img(random_num.get(timer_cnt)));
                    timer_cnt++;
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,1000,1000);
    }

    //몇번째 inputView의 이미지를 바꿀지 선택
    public void setInputImg(int cur,int img_id){
        //1 <= cur <= 6 로 예상
        switch (cur){
            case 0:
                input_1.setImageResource(img_id);
                break;
            case 1:
                input_2.setImageResource(img_id);
                break;
            case 2:
                input_3.setImageResource(img_id);
                break;
            case 3:
                input_4.setImageResource(img_id);
                break;
            case 4:
                input_5.setImageResource(img_id);
                break;
            case 5:
                input_6.setImageResource(img_id);
                break;
        }
        cur_input++;
    }

    public void setBoard_image(int board_num, final int image){
        switch (board_num){
            case 1 :
                board_1.post(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(image);
                    }
                });
                break;
            case 2 :
                board_2.post(new Runnable() {
                    @Override
                    public void run() {
                        board_2.setImageResource(image);
                    }
                });
                break;
            case 3 :
                board_3.post(new Runnable() {
                    @Override
                    public void run() {
                        board_3.setImageResource(image);
                    }
                });
                break;
            case 4 :
                board_4.post(new Runnable() {
                    @Override
                    public void run() {
                        board_4.setImageResource(image);
                    }
                });
                break;
            case 5 :
                board_5.post(new Runnable() {
                    @Override
                    public void run() {
                        board_5.setImageResource(image);
                    }
                });
                break;
            case 6 :
                board_6.post(new Runnable() {
                    @Override
                    public void run() {
                        board_6.setImageResource(image);
                    }
                });
                break;
            case 7 :
                board_7.post(new Runnable() {
                    @Override
                    public void run() {
                        board_7.setImageResource(image);
                    }
                });
                break;
            case 8 :
                board_8.post(new Runnable() {
                    @Override
                    public void run() {
                        board_8.setImageResource(image);
                    }
                });
                break;
            case 9 :
                board_9.post(new Runnable() {
                    @Override
                    public void run() {
                        board_9.setImageResource(image);
                    }
                });
                break;
            case 10 :
                board_10.post(new Runnable() {
                    @Override
                    public void run() {
                        board_10.setImageResource(image);
                    }
                });
                break;
            case 11 :
                board_11.post(new Runnable() {
                    @Override
                    public void run() {
                        board_11.setImageResource(image);
                    }
                });
                break;
            case 12 :
                board_12.post(new Runnable() {
                    @Override
                    public void run() {
                        board_12.setImageResource(image);
                    }
                });
                break;
            case 13 :
                board_13.post(new Runnable() {
                    @Override
                    public void run() {
                        board_13.setImageResource(image);
                    }
                });
                break;
            case 14 :
                board_14.post(new Runnable() {
                    @Override
                    public void run() {
                        board_14.setImageResource(image);
                    }
                });
                break;
            case 15 :
                board_15.post(new Runnable() {
                    @Override
                    public void run() {
                        board_15.setImageResource(image);
                    }
                });
                break;
            case 16 :
                board_16.post(new Runnable() {
                    @Override
                    public void run() {
                        board_16.setImageResource(image);
                    }
                });
                break;
        }
    }
}