package functions;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class Update {
    BasicDBObject document;
    BasicDBObject basicDBObject2;

    public  void EditStock(String ProductName,String REProductName,
                          String UnitPrize,String REUnitPrize,
                          String Quantity,String REQuantity,
                          String Supplier,String RESupplier,
                          MongoCollection<BasicDBObject> Collection) {
        if (!(ProductName == null)){
            this.document.put("ProductName", ProductName);
            this.basicDBObject2.put("ProductName",REProductName);}
        if (!(UnitPrize == null)){
            this.document.put("UnitPrize", UnitPrize);
            this.basicDBObject2.put("UnitPrize",REUnitPrize);}
        if (!(Quantity == null)){
            this.document.put("Quantity", Quantity);
            this.basicDBObject2.put("Quantity",REQuantity);}
        if (!(Supplier == null)) {
            this.document.put("Supplier",Supplier);
            this.basicDBObject2.put("Supplier",RESupplier);}
        Collection.insertOne(document);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", this.basicDBObject2);
        Collection.updateOne(this.document, updateObject);

    }

}
