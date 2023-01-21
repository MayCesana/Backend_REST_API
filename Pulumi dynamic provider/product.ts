import * as pulumi from "@pulumi/pulumi"
import { output } from "@pulumi/pulumi"
import { ProductProvider } from "./productProvider"

export interface ProductInputs {
    _id: pulumi.Input<number>
    name: pulumi.Input<string>
    brand: pulumi.Input<string>
    price: pulumi.Input<string>
    category: pulumi.Input<string>
}

export class Product extends pulumi.dynamic.Resource {

    public readonly idNumber: pulumi.Output<number>;
    public readonly id: pulumi.Output<string>;
    public readonly name!:  pulumi.Output<string>;

    constructor(name: string, props: ProductInputs, 
        opts?: pulumi.CustomResourceOptions) {
            super(new ProductProvider(), name, props, opts);
            this.idNumber = pulumi.output(props._id);
            this.id = pulumi.output(props._id.toString());
    }
}