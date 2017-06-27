import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wolkabout.hexiwear.activity.ReadingsActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class test {

    private DatabaseReference firebaseReference;
    private FirebaseDatabase firebaseDBInstance;



    @Before
    public void init() {
        firebaseDBInstance = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDBInstance.getReference("HR");
    }

    @Test
    public void humidityValidation() throws Exception{
        Assert.assertEquals(ReadingsActivity.getDataReading(), firebaseReference.addListenerForSingleValueEvent());
        //( ReadingsActivity.getDataValue().equals(firebaseReference.addListenerForSingleValueEvent()),is(true))


    }

    @Test
    public void lightValidation() throws Exception{
        Assert.assertEquals(ReadingsActivity.getDataReading(), firebaseReference.addListenerForSingleValueEvent());
        //assertThat( ReadingsActivity.getDataValue().equals(firebaseReference.addListenerForSingleValueEvent()),is(true))

    }

    @Test
    public void tempuratureValidation() throws Exception{
        Assert.assertEquals(ReadingsActivity.getDataReading(), firebaseReference.addListenerForSingleValueEvent());
       // assertThat( ReadingsActivity.getDataValue().equals(firebaseReference.addListenerForSingleValueEvent()),is(true))

    }

}