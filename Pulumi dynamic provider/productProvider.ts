import * as pulumi from "@pulumi/pulumi";
import { Resource } from "@pulumi/pulumi";
//import { RequestInfo, RequestInit } from 'node-fetch';
import fetch from "node-fetch";
//const fetch = (url: RequestInfo, init?: RequestInit) =>
  //import('node-fetch').then(({ default: fetch }) => fetch(url, init));

export interface ProductProviderInputs {
    _id: number
    name: string
    brand: string
    price: string
    category: string
}

export class ProductProvider implements pulumi.dynamic.ResourceProvider {

    async create(inputs: ProductProviderInputs) : Promise<pulumi.dynamic.CreateResult> {
        const url ="http://localhost:8080/products";
        console.log(inputs._id);
        const response = await fetch(url, {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            id: inputs._id,
            name: inputs.name,
            brand: inputs.brand,
            price: inputs.price,
            category: inputs.category
            })
        });
        if (!response.ok) {
            throw new Error(`Failed to create the product, status: ${response.status}`);
        }

        const json = await response.json();
        // return the ID of the resource
        console.log(json.id.toString());
        return {
            id: json.id.toString(),
            outs: {
                name: inputs.name,
                brand: inputs.brand,
                price: inputs.price,
                category: inputs.category
            }
        };
    }

    async delete(id: string, props: any) {
        let idNum: number = parseInt(id, 10);
        const uri = "http://localhost:8080/products/" + idNum
        const response = await fetch(uri, {
            method: 'DELETE'
        })
        console.log(response);
        if (response.status !== 204) {
            throw new Error(`Error. status: ${response.status}`);
        }
    }
        
    
}


