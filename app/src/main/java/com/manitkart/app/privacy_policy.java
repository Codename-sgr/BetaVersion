package com.manitkart.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class privacy_policy extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private privPolAdapter privPolAdapter;
    private ArrayList<privPolModel> privPolModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        recyclerView = findViewById(R.id.rec);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        privPolModelArrayList.add(new privPolModel("Your privacy is a matter of prime concern for us. Accordingly, we have developed this Policy to detail what information we may hold about you, why we hold it, and how we may use it.\n" +
                "Before or at the time of collecting personal information, we will identify the purposes for which information is being collected.\n" +
                "We will collect and use your personal information solely with the objective of fulfilling those purposes specified by us and for other compatible purposes.\n" +
                "Further, we will only retain personal information as long as necessary for the fulfillment of those purposes.\n" +
                "We will collect personal information by lawful and fair means and, wherever appropriate, with the knowledge or consent of the individual concerned.\n" +
                "We will protect personal information by reasonable security safeguards against loss or theft, as well as unauthorized access, disclosure, copying, use or modification.\n" +
                "We are committed to working with these principles in order to ensure that the confidentiality of personal information is protected and maintained."));

        privPolModelArrayList.add(new privPolModel("<b>What data do we collect?</b>"));

        privPolModelArrayList.add(new privPolModel("When you sign up to MANITKart, we require your name and email address. Further, while browsing the app, we may require you to provide the requested permissions of access to internet and storage."));





        privPolModelArrayList.add(new privPolModel("<b>Why we use your information?</b>"));

        privPolModelArrayList.add(new privPolModel("The personal information we store may be used to tailor our service to you. We may also use your information including personal and technical for internal purposes such as statistical analysis. This is so that we can understand how our products and services are being used and to aid the decisions we make"));






        privPolModelArrayList.add(new privPolModel("<b>Image Policy</b>"));

        privPolModelArrayList.add(new privPolModel("Buyers on MANITKart take their purchase decisions based on the images presented in a listing. Hence, photographs in the listing should be taken by the seller and must be of the actual item for sale. Generic photos of the same or similar products copied from a different website are prohibited.\n" +
                "In addition, image clarity is important to ensure that all flaws and blemishes (if any) of the product are accurately represented. Images that are blurred or distorted will be removed."));



        privPolModelArrayList.add(new privPolModel("<b>Listings Removals</b>"));

        privPolModelArrayList.add(new privPolModel("We reserve the right to remove any item that is deemed as unsuitable due to any of the following reasons:\n" +
                "•\tContains content that is defamatory, threatening, obscene, offensive, sexually explicit or harmful in any way.\n" +
                "•\tViolates image policy"));



        privPolModelArrayList.add(new privPolModel("<b>Children’s Privacy</b>"));

        privPolModelArrayList.add(new privPolModel("This application is not designed for use by those under the age of 14; those of this age, or below, should also refrain from supplying any data of a personal nature. To this effect, we only recommend the use of this application for those over the age of 18. We may check the age of any user of the application and if we find that a child under the age of 14 has provided us with personal information, we immediately remove this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions."));


        privPolModelArrayList.add(new privPolModel("<b>Changes to This Privacy Policy</b>"));

        privPolModelArrayList.add(new privPolModel("We have the discretion to update this privacy policy at any time. When we do, we will revise the updated date at the top of this page. We encourage users to frequently check this page for any changes to stay informed about how we are helping to protect the personal information we collect. "));


        privPolModelArrayList.add(new privPolModel("<b>Contact Us</b>"));

        privPolModelArrayList.add(new privPolModel("If you have questions or concerns about our privacy practices, please feel free to contact us."));

        privPolAdapter = new privPolAdapter(this, privPolModelArrayList);
        recyclerView.setAdapter(privPolAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
