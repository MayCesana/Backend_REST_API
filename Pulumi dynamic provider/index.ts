import {Product} from "./product"

const newProduct = new Product("newProduct",
    {
        _id: 1,
        name: "shirt1",
        brand: "Nike",
        price: "120",
        category: "Shirt"
    }
);

const idString = newProduct.id.apply(id => id.toString());

export const newProductId = idString;
export const newProductName = newProduct.name;


