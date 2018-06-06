package com.munjihye.docketmon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private int stage_cnt = 0;
    private int cur_input = 0;
    private int[] random_num = new int[6];
    private int[] submit_num = new int[6];
    private Thread thread = new Thread();
    private FindImage findImage = new FindImage();

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


    private ImageView input_1;
    private ImageView input_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        ImageView stage = (ImageView)findViewById(R.id.stage);

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

        input_1 = (ImageView)findViewById(R.id.input_1);
        input_2 = (ImageView)findViewById(R.id.input_2);



        int stg_img = findImage.selectStageImg(stage_cnt);
        //게임끝
        if (stg_img == 0){
            //전체 화면 덮기
        }
        stage.setImageResource(stg_img);

        //해당 board num을 제출 배열에 따로 저장해둬서 submit버튼 누르면 정답배열이랑 비교해야함
        board_1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 1;
                selectInputImg(cur_input,R.drawable.board_init_1);}});

        board_2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 2;
                selectInputImg(cur_input,R.drawable.board_init_2);}});
        board_3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 3;
                selectInputImg(cur_input,R.drawable.board_init_3);}
        });
        board_4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 4;
                selectInputImg(cur_input,R.drawable.board_init_4);}
        });
        board_5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 5;
                selectInputImg(cur_input,R.drawable.board_init_5);}
        });
        board_6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 6;
                selectInputImg(cur_input,R.drawable.board_init_6);}
        });
        board_7.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 7;
                selectInputImg(cur_input,R.drawable.board_init_7);}
        });
        board_8.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 8;
                selectInputImg(cur_input,R.drawable.board_init_8);}
        });
        board_9.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 9;
                selectInputImg(cur_input,R.drawable.board_init_9);}
        });
        board_10.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 10;
                selectInputImg(cur_input,R.drawable.board_init_10);}
        });
        board_11.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 11;
                selectInputImg(cur_input,R.drawable.board_init_11);}
        });
        board_12.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 12;
                selectInputImg(cur_input,R.drawable.board_init_12);}
        });
        board_13.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 13;
                selectInputImg(cur_input,R.drawable.board_init_13);}
        });
        board_14.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 14;
                selectInputImg(cur_input,R.drawable.board_init_14);}
        });
        board_15.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 15;
                selectInputImg(cur_input,R.drawable.board_init_15);}
        });
        board_16.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                submit_num[cur_input] = 16;
                selectInputImg(cur_input,R.drawable.board_init_16);}
        });


        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //일정 시간마다 랜덤수 발생 & board 배경이미지로 초기화
                        //랜덤수는 정답배열에 따로 저장해둠
                        //랜덤수에 따라 해당하는 board 이미지 변환
                        //board랑 input 이미지 초기화도.....

                        if(stage_cnt < 3){
                            playGame(4);

                        }else if(stage_cnt >=3 && stage_cnt <6){
                            playGame(5);
                        }else if(stage_cnt >=6 && stage_cnt <9){
                           playGame(6);
                        }else if(stage_cnt == 9){
                            //종료
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
                    }
                }
        );


    }

    /*
    //현재 스테이지 이미지 선택
    public int selectStageImg(int stage_cnt){

        //switch를 통해 해당하는 이미지 id return
        if (stage_cnt < 3)
            return R.drawable.stage_1;
        else if (stage_cnt >= 3 && stage_cnt < 6)
            return R.drawable.stage_2;
        else if (stage_cnt >=6 && stage_cnt < 9)
            return R.drawable.stage_3;

        //이제 게임끝
        return 0;
    }
*/
    //몇번째 inputView의 이미지를 바꿀지 선택
    public void selectInputImg(int cur,int img_id){
        //1 <= cur <= 6 로 예상
        switch (cur){
            case 0:
                input_1.setImageResource(img_id);
                cur_input++;
                break;
            case 1:
                input_2.setImageResource(img_id);
                cur_input++;
                break;
        }
    }

    public void playGame(int count) {
        for (int i = 0; i < count; i++) {
            //random num 생성
            Random random = new Random();
            random_num[i] = random.nextInt(16) + 1;
            findImage.selectBoard_img(random_num[i]);
            //화면에 1초동안 띄우는 코드 여기에 추가!!
            findImage.selectBoard_init_img(random_num[i]);

        }
    }

/*
    public void blinkBoardImg (int ran_num) throws InterruptedException {

        switch (ran_num){
            case 1 :
                board_1.setImageResource(R.drawable.board_1);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_1.setImageResource(R.drawable.board_init_1);
                break;
            case 2 :
                board_2.setImageResource(R.drawable.board_2);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_2.setImageResource(R.drawable.board_init_2);
                break;
            case 3 :
                board_3.setImageResource(R.drawable.board_3);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_3.setImageResource(R.drawable.board_init_3);
                break;
            case 4 :
                board_4.setImageResource(R.drawable.board_4);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_4.setImageResource(R.drawable.board_init_4);
                break;
            case 5 :
                board_5.setImageResource(R.drawable.board_5);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_5.setImageResource(R.drawable.board_init_5);
                break;
            case 6 :
                board_6.setImageResource(R.drawable.board_6);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_6.setImageResource(R.drawable.board_init_6);
                break;
            case 7 :
                board_7.setImageResource(R.drawable.board_7);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_7.setImageResource(R.drawable.board_init_7);
                break;
            case 8 :
                board_8.setImageResource(R.drawable.board_8);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_8.setImageResource(R.drawable.board_init_8);
                break;
            case 9 :
                board_9.setImageResource(R.drawable.board_9);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_9.setImageResource(R.drawable.board_init_9);
                break;
            case 10 :
                board_10.setImageResource(R.drawable.board_10);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_10.setImageResource(R.drawable.board_init_10);
                break;
            case 11 :
                board_11.setImageResource(R.drawable.board_11);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_11.setImageResource(R.drawable.board_init_11);
                break;
            case 12 :
                board_12.setImageResource(R.drawable.board_12);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_12.setImageResource(R.drawable.board_init_12);
                break;
            case 13 :
                board_13.setImageResource(R.drawable.board_13);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_13.setImageResource(R.drawable.board_init_13);
                break;
            case 14 :
                board_14.setImageResource(R.drawable.board_14);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_14.setImageResource(R.drawable.board_init_14);
                break;
            case 15 :
                board_15.setImageResource(R.drawable.board_15);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_15.setImageResource(R.drawable.board_init_15);
                break;
            case 16 :
                board_16.setImageResource(R.drawable.board_16);
                //일정 시간 딜레이
                thread.sleep(3000);
                board_16.setImageResource(R.drawable.board_init_16);
                break;

        }
    }
*/

    /*
    public void blinkBoardImg (int ran_num){

        Handler delayHandler = new Handler();

        switch (ran_num){
            case 1 :
                board_1.setImageResource(R.drawable.board_1);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_1);
                    }
                }, 3000);
                break;
            case 2 :
                board_2.setImageResource(R.drawable.board_2);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_2);
                    }
                }, 3000);
                break;
            case 3 :
                board_3.setImageResource(R.drawable.board_3);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_3);
                    }
                }, 3000);
                break;
            case 4 :
                board_4.setImageResource(R.drawable.board_4);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_4);
                    }
                }, 3000);
                break;
            case 5 :
                board_5.setImageResource(R.drawable.board_5);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_5);
                    }
                }, 3000);
                break;
            case 6 :
                board_6.setImageResource(R.drawable.board_6);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_6);
                    }
                }, 3000);
                break;
            case 7 :
                board_7.setImageResource(R.drawable.board_7);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_7);
                    }
                }, 3000);
                break;
            case 8 :
                board_8.setImageResource(R.drawable.board_8);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_8);
                    }
                }, 3000);
                break;
            case 9 :
                board_9.setImageResource(R.drawable.board_9);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_9);
                    }
                }, 3000);
            case 10 :
                board_10.setImageResource(R.drawable.board_10);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_10);
                    }
                }, 3000);
                break;
            case 11 :
                board_11.setImageResource(R.drawable.board_11);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_11);
                    }
                }, 3000);
                break;
            case 12 :
                board_12.setImageResource(R.drawable.board_12);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_12);
                    }
                }, 3000);
                break;
            case 13 :
                board_13.setImageResource(R.drawable.board_13);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_13);
                    }
                }, 3000);
                break;
            case 14 :
                board_14.setImageResource(R.drawable.board_14);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_14);
                    }
                }, 3000);
                break;
            case 15 :
                board_15.setImageResource(R.drawable.board_15);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_15);
                    }
                }, 3000);
                break;
            case 16 :
                board_16.setImageResource(R.drawable.board_16);
                //1초후 아래 코드실행
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        board_1.setImageResource(R.drawable.board_init_16);
                    }
                }, 3000);
                break;

        }
    }
    */
}
