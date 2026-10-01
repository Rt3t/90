package com.habitflow.mobile;

import android.app.*;import android.os.*;import android.graphics.Color;import android.graphics.Typeface;import android.content.*;import android.view.*;import android.widget.*;import org.json.*;import java.io.*;import java.net.*;import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    LinearLayout list; TextView status; final String API="http://10.0.2.2:8080"; final int USER_ID=1;
    int dp(float v){return (int)(v*getResources().getDisplayMetrics().density+.5f);}
    TextView tv(String s,float size){ TextView t=new TextView(this); t.setText(s);t.setTextSize(size);t.setTextColor(Color.rgb(34,34,34));t.setPadding(dp(12),dp(10),dp(12),dp(10));return t; }
    @Override public void onCreate(Bundle b){super.onCreate(b); build(); load();}
    void build(){
        LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(16),dp(12),dp(16),dp(12));root.setBackgroundColor(Color.rgb(245,247,248));
        TextView title=tv("HabitFlow",28);title.setTypeface(Typeface.DEFAULT,Typeface.BOLD);root.addView(title,new LinearLayout.LayoutParams(-1,dp(55)));
        TextView sub=tv("Трекер привычек и личной продуктивности",15);sub.setTextColor(Color.DKGRAY);root.addView(sub,new LinearLayout.LayoutParams(-1,dp(45)));
        Button add=new Button(this);add.setText("+ Добавить привычку");add.setOnClickListener(v->dialog());root.addView(add,new LinearLayout.LayoutParams(-1,dp(52)));
        status=tv("Загрузка...",14);root.addView(status,new LinearLayout.LayoutParams(-1,dp(40)));
        ScrollView scroll=new ScrollView(this);list=new LinearLayout(this);list.setOrientation(LinearLayout.VERTICAL);scroll.addView(list);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));setContentView(root);
    }
    void load(){new Thread(()->{try{String s=get(API+"/api/habits/"+USER_ID);JSONArray a=new JSONArray(s);runOnUiThread(()->render(a));}catch(Exception e){runOnUiThread(()->status.setText("Backend недоступен. Запусти docker compose up --build"));}}).start();}
    void render(JSONArray a){list.removeAllViews();status.setText("Привычек: "+a.length());for(int i=0;i<a.length();i++){try{JSONObject h=a.getJSONObject(i);long id=h.getLong("id");String title=h.optString("title");int streak=h.optInt("streak");LinearLayout card=new LinearLayout(this);card.setOrientation(LinearLayout.VERTICAL);card.setPadding(dp(12),dp(8),dp(12),dp(8));card.setBackgroundColor(Color.WHITE);TextView t=tv(title,19);t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);card.addView(t);TextView st=tv("Серия: "+streak+" дней",15);st.setTextColor(Color.DKGRAY);card.addView(st);Button done=new Button(this);done.setText("✓ Выполнить сегодня");done.setOnClickListener(v->complete(id));card.addView(done);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,dp(145));p.setMargins(0,0,0,dp(10));list.addView(card,p);}catch(Exception ignored){}}}
    void dialog(){final EditText input=new EditText(this);input.setHint("Название привычки");new AlertDialog.Builder(this).setTitle("Новая привычка").setView(input).setNegativeButton("Отмена",null).setPositiveButton("Создать",(d,w)->{String title=input.getText().toString().trim();if(!title.isEmpty())create(title);}).show();}
    void create(String title){new Thread(()->{try{JSONObject body=new JSONObject();body.put("title",title);body.put("description","");post(API+"/api/habits?userId="+USER_ID,body.toString());runOnUiThread(this::load);}catch(Exception e){runOnUiThread(()->Toast.makeText(this,"Ошибка создания",Toast.LENGTH_SHORT).show());}}).start();}
    void complete(long id){new Thread(()->{try{put(API+"/api/habits/"+id+"/complete");runOnUiThread(this::load);}catch(Exception e){runOnUiThread(()->Toast.makeText(this,"Ошибка соединения",Toast.LENGTH_SHORT).show());}}).start();}
    String get(String u)throws Exception{return req("GET",u,null);}String post(String u,String b)throws Exception{return req("POST",u,b);}String put(String u)throws Exception{return req("PUT",u,null);}
    String req(String method,String u,String body)throws Exception{HttpURLConnection c=(HttpURLConnection)new URL(u).openConnection();c.setRequestMethod(method);c.setConnectTimeout(5000);c.setReadTimeout(5000);c.setRequestProperty("Content-Type","application/json");if(body!=null){c.setDoOutput(true);try(OutputStream o=c.getOutputStream()){o.write(body.getBytes(StandardCharsets.UTF_8));}}int code=c.getResponseCode();InputStream in=code>=400?c.getErrorStream():c.getInputStream();if(in==null)return "";try(BufferedReader r=new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8))){StringBuilder s=new StringBuilder();String l;while((l=r.readLine())!=null)s.append(l);return s.toString();}}
}
