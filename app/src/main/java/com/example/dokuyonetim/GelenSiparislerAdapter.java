package com.example.dokuyonetim;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GelenSiparislerAdapter extends FirestoreRecyclerAdapter<GelenSiparislerValues, GelenSiparislerAdapter.Holder> {
    Bundle bundle = new Bundle();
    ProgressDialog pd;


    public GelenSiparislerAdapter(@NonNull FirestoreRecyclerOptions<GelenSiparislerValues> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int i, @NonNull GelenSiparislerValues model) {
        holder.siparisdurumu.setText(model.getSiparisDurumu());
        holder.fiyat.setText(model.getOdenenTutar());
        holder.siparistarihi.setText(model.getSiparisTarihi());
        holder.siparisno.setText(model.getSiparisNumarasi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getAdresData(model.getSiparisNumarasi(), model);
                pd = new ProgressDialog(holder.itemView.getContext());
                pd.show();
                FirebaseFirestore.getInstance().collection("Siparişler").document(model.getSiparisNumarasi())
                        .collection("Adres").document("adres")
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot sp = task.getResult();
                        bundle.putString("Ad Soyad", String.valueOf(sp.get("Ad Soyad")));
                        bundle.putString("Adres", String.valueOf(sp.get("Adres")));
                        bundle.putString("Adres Başlığı", String.valueOf(sp.get("Adres Başlığı")));
                        bundle.putString("Telefon no", String.valueOf(sp.get("Telefon no")));
                        bundle.putString("İlİlçe", String.valueOf(sp.get("İlİlçe")));

                    }
                }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            bundle.putString("siparisdurumu", model.getSiparisDurumu());
                            bundle.putString("fiyat", model.getOdenenTutar());
                            bundle.putString("siparistarihi", model.getSiparisTarihi());
                            bundle.putString("siparisno", model.getSiparisNumarasi());

                            Intent intent = new Intent(holder.itemView.getContext(), GelenSiparisAyrinti.class);
                            intent.putExtras(bundle);
                            holder.itemView.getContext().startActivity(intent);
                            pd.dismiss();
                        }
                    }
                });



            }
        });
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gelensiparisitem, parent, false);
        return new Holder(view);

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView siparisno, siparistarihi, fiyat, siparisdurumu;


        public Holder(@NonNull View itemView) {
            super(itemView);

            siparisno = itemView.findViewById(R.id.siparisno);
            siparistarihi = itemView.findViewById(R.id.siparistarihi);
            fiyat = itemView.findViewById(R.id.toplamtutar);
            siparisdurumu = itemView.findViewById(R.id.siparisdurumu);
        }
    }
}
