package com.example.hussain.qurantilawat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        displayList();
    }

    private void displayList() {

        ListView list = (ListView) findViewById(R.id.quranList);

        String[] quranList = new String[] {"0. Surat Al-Fatiha","1. Surat Al-Baqara","2. Surat Aal-E-Imran","3. Surat An-Nisa","4. Surat Al-Maeda","5. Surat Al-Anaam","6. Surat Al-Araf","7. Surat Al-Anfal","8. Surat At-Tawba","9. Surat Yunus","10. Surat Hud","11. Surat Yusuf","12. Surat Ar-Rad","13. Surat Ibrahim","14. Surat Al-Hijr","15. Surat An-Nahl","16. Surat Al-Isra","17. Surat Al-Kahf","18. Surat Maryam","19. Surat Ta-Ha","20. Surat Al-Anbiya","21. Surat Al-Hajj","22. Surat Al-Mumenoon","23. Surat An-Noor","24. Surat Al-Furqan","25. Surat Ash-Shuara","26. Surat An-Naml","27.Surat Al-Qasas","28. Surat Al-Ankaboot","29. Surat Ar-Room","30. Surat Luqman","31. Surat As-Sajda","32. Surat Al-Ahzab","33. Surat Saba","34. Surat Fatir","35. Surat Ya-Seen","36. Surat As-Saaffat","37. Surat Sad","38. Surat Az-Zumar","39. Surat Ghafir","40. Surat Fussilat","41. Surat Ash-Shura","42. Surat Az-Zukhruf","43. Surat Ad-Dukhan","44. Surat Al-Jathiya","45. Surat Al-Ahqaf","46. Surat Muhammad","47. Surat Al-Fath","48. Surat Al-Hujraat","49. Surat Qaf","50. Surat Adh-Dhariyat","51. Surat At-Tur","52. Surat An-Najm","53. Surat Al-Qamar","54. Surat Ar-Rahman","55. Surat Al-Waqia","56. Surat Al-Hadid","57. Surat Al-Mujadila","58. Surat Al-Hashr","59. Surat Al-Mumtahina","60. Surat As-Saff","61. Surat Al-Jumua","62. Surat Al-Munafiqoon","63. Surat At-Taghabun","64. Surat At-Talaq","65. Surat At-Tahrim","66. Surat Al-Mulk","67. Surat Al-Qalam","68. Surat Al-Haaqqa","69. Surat Al-Maarij","70. Surat Nooh","71. Surat Al-Jinn","72. Surat Al-Muzzammil","73. Surat Al-Muddaththir","74. Surat Al-Qiyama","75. Surat Al-Insan","76. Surat Al-Mursalat","77. Surat An-Naba","78. Surat An-Naziat","79. Surat Abasa","80. Surat At-Takwir","81. Surat Al-Infitar","82. Surat Al-Mutaffifin","83. Surat Al-Inshiqaq","84. Surat Al-Burooj","85. Surat At-Tariq","86. Surat Al-Ala","87. Surat Al-Ghashiya","88. Surat Al-Fajr","89. Surat Al-Balad","90. Surat Ash-Shams","91. Surat Al-Lail","92. Surat Ad-Dhuha","93. Surat Al-Inshirah","94. Surat At-Tin","95. Surat Al-Alaq","96. Surat Al-Qadr","97. Surat Al-Bayyina","98. Surat Az-Zalzala","99. Surat Al-Adiyat","100. Surat Al-Qaria","101. Surat At-Takathur","102. Surat Al-Asr","103. Surat Al-Humaza","104. Surat Al-Fil","105. Surat Quraish","106. Surat Al-Maun","107. Surat Al-Kauther","108. Surat Al-Kafiroon","109. Surat An-Nasr","110. Surat Al-Masadd","111. Surat Al-Ikhlas","112. Surat Al-Falaq","113. Surat An-Nas"};

        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,R.layout.rowtable,R.id.textVw,quranList);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.print(position);

                Intent intent= new Intent(MainActivity.this, QuranPlayList.class);

                intent.putExtra("s",position);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
