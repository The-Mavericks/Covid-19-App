package com.example.chatbot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class other extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> itemList;
    ItemArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other);
        recyclerView = findViewById(R.id.list);
        itemList = new ArrayList<>();
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        itemArrayAdapter = new ItemArrayAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);
    }

    private void initData() {
        itemList.add(new Item("Drop in China's air pollution after coronavirus lockdown", "The pollution monitoring satellites of the space agencies detected significant decreases over China in nitrogen dioxide (NO2), a noxious gas emitted by motor vehicles, power plants, and industrial facilities, NASA said in a statement. This was seen as a result of the economic slowdown following the deadly coronavirus outbreak. The same trend has been seen is various parts of the world.\nBut these changes are temporary and the environment will revert back to its original condition once the lockdown is lifted. We must learn and take this as an opportunity  and learn to make conscience efforts to decrease air pollution.", R.drawable.china_pollution));
        itemList.add(new Item("Improvement in air quality", "The nationwide 'Janta Curfew' followed by the 21-day lockdown in India to combat the coronavirus outbreak has led to a significant reduction, a Central Pollution Control Board report has stated. Residents of Punjab’s Jalandhar are now able to view the mighty Dhauladhar mountain range located several kilometers away in Himachal Pradesh as the city’s air quality has improved significantly, as shown in the picture above.", R.drawable.punjab));
        itemList.add(new Item("Mental health during COVID 19", "1.Social distance, but connect emotionally..\n2. Maintain exercise and physical activity.\n3. Balance nutrition\n4. Set boundaries for social media consumption.", R.drawable.white));
        itemList.add(new Item("Exercise and Stay Fit", "1. Climb up the stairs as much as you can, think of it as an opportunity to be active.\n2. Use household chores as a way to be more physical activity.\n3. Do some muscle strengthening activities such as lifting weights or improvise using full bottles of water or simply use your own body weight and do sets of press ups, sit ups and squats.\n4. Make time for fun, such as dancing to music.", R.drawable.exercise));
        itemList.add(new Item("Nutrition Tips for Reducing Your Carbon Footprint", "1. Stop wasting food:\nFood waste is a major contributor to greenhouse gas emissions. That’s because food that’s thrown away decomposes in landfills and emits methane, a particularly potent greenhouse gas.\n\n2. Eat less meat:\nThis is because the emissions from livestock production — especially beef and dairy cattle — represent 14.5% of the globe’s human-induced greenhouse gas emissions.\n\n3. Eat more fiber-rich foods:\nThese foods may help keep you full, naturally limiting your intake of items with a heavy carbon load.\n\n4. Ditch the plastic:\nForego plastic bags and plastic wrap when purchasing fresh produce and bring your own grocery bags to the store.", R.drawable.food));
        itemList.add(new Item("WHO Tips", "For more information please check our WHO update corner. And incase of any doubts talk to our bot rand we will try solving your problem.", R.drawable.be_smart_inform));
        itemList.add(new Item("Help others", "1. Look after yourself, to look after us all – flatten the curve!\n 2. Combat misinformation online \n3. Fundraise for people financially impacted by quarantine measures\n 4. Share positive news and acts of kindness with your community", R.drawable.white));
        itemList.add(new Item("Pregnancy during COVID 19", "If you have tested positive for COVID-19 during your pregnancy or just before delivery, don’t worry. Initial studies indicate that the virus does not transmit to uterus, or through breastmilk. However, it is important to ensure that the newborn does not get infected through contact with someone who has tested positive for the disease. ", R.drawable.who_pregnancy));

    }
}
