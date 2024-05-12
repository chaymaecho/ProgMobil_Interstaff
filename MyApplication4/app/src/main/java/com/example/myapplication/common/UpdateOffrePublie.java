package com.example.myapplication.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Connection;
import com.example.myapplication.models.OffreE;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateOffrePublie extends FirebaseRecyclerAdapter<OffreE, UpdateOffrePublie.PublishedVacancyHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UpdateOffrePublie(@NonNull FirebaseRecyclerOptions<OffreE> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull PublishedVacancyHolder holder, final int position, @NonNull OffreE model) {
        holder.title.setText("Profession. "+model.getJobTitle());
        holder.description.setText("Plus de détail. "+model.getDescription());
        holder.salary.setText("Salaire. " + model.getSalary() + " Є/mois");
        holder.organization.setText("Societé. " + model.getOrganization());


        String temp = model.getDeadline();
        holder.date.setText("Date limite :"+temp);

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.description.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_offre))
                        .setExpanded(true, 1780)
                        .create();


                View view = dialogPlus.getHolderView();

                EditText salary = view.findViewById(R.id.etUpdateSalary);
                EditText title = view.findViewById(R.id.etUpdateJobTitle);
                EditText description = view.findViewById(R.id.etUpdateDescription);
                EditText org = view.findViewById(R.id.etUpdateOrg);
                DatePicker datePicker = view.findViewById(R.id.dpUpdateDeadline);

                Button btnUp = view.findViewById(R.id.btnUpdateInfo);


                salary.setText(model.getSalary());
                title.setText(model.getJobTitle());
                description.setText(model.getDescription());
                org.setText(model.getOrganization());

                Date d = new Date(model.getDeadline());

                datePicker.updateDate(d.getYear() + 1900, d.getMonth(), d.getDate());

                dialogPlus.show();


                btnUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Connection con = new Connection();

                        String date = "" + datePicker.getYear() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth();
                        String desPattern = ".{1,200}";

                        //check description limit lower than 200 characters
                        if (!(description.getText().toString()).matches(desPattern))
                            Toast.makeText(holder.btnUpdate.getContext(), "Too long Description", Toast.LENGTH_LONG);

                        else {
                            //put data into Hashmap
                            Map<String, Object> map = new HashMap<>();
                            map.put("deadline", date);
                            map.put("description", description.getText().toString());
                            map.put("jobTitle", title.getText().toString());
                            map.put("organization", org.getText().toString());
                            map.put("salary", salary.getText().toString());

                            int mLastPosition = holder.getAdapterPosition();
                            //add map into firebase
                            con.getRef().child(ConstantsCommun.VACANCY).child(getRef(mLastPosition).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.itemView.getContext(), "Updated Successfully", Toast.LENGTH_LONG);
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(holder.itemView.getContext(), "Error While Updating", Toast.LENGTH_LONG);
                                            dialogPlus.dismiss();
                                        }
                                    });
                        }
                    }


                });

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create alert box
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.description.getContext());
                builder.setTitle("Etes vous sur?");
                builder.setMessage("Les données vont etre supprimer");

                //delete button
                builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Connection con = new Connection();
                        int mLastPosition = holder.getAdapterPosition();

                        //remove data in firebase
                        con.getRef().child(ConstantsCommun.VACANCY).child(getRef(mLastPosition).getKey()).removeValue();

                        Toast.makeText(holder.btnDelete.getContext(), "suprimée.", Toast.LENGTH_LONG);
                    }
                });
                //cancel button
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.btnDelete.getContext(), "Annulé.", Toast.LENGTH_LONG);

                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public PublishedVacancyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_supprime_offre, parent, false);

        return new PublishedVacancyHolder(view);
    }

    class PublishedVacancyHolder extends RecyclerView.ViewHolder {

        TextView title, description, salary, organization,date;
        Button btnUpdate, btnDelete;

        public PublishedVacancyHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvUpdateJobTitle);
            description = itemView.findViewById(R.id.tvUpdateJobDescription);
            salary = itemView.findViewById(R.id.tvUpdateJobSalary);
            organization =itemView.findViewById(R.id.tvUpdateJoborg);
            date = itemView.findViewById(R.id.tvUpdateJobDate);
            btnUpdate = itemView.findViewById(R.id.btnVblockUpdate);
            btnDelete = itemView.findViewById(R.id.btnVblockRemove);

        }
    }

}
