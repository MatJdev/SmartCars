package com.jetpackcompose.smartcars.model

import com.jetpackcompose.smartcars.ui.data.model.Car
import org.web3j.protocol.Web3j
import org.web3j.crypto.Credentials
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider

object Web3jSingleton {
    private lateinit var web3j: Web3j
    private lateinit var credentials: Credentials
    private lateinit var carRentalContract: Car // Cambiar por CarRentalContract la clase del contrato

    fun init() {
        //Descomentar cuando se agregue el contract
        /*// Inicializar Web3j
        val infuraUrl = "https://rinkeby.infura.io/v3/your_project_id"
        val httpProvider = HttpService(infuraUrl)
        web3j = Web3j.build(httpProvider)

        // Inicializar credenciales
        val privateKey = "your_private_key_here"
        credentials = Credentials.create(privateKey)

        // Inicializar instancia del contrato
        val contractAddress = "your_contract_address_here"
        // Descomentar cuando se agregue el contrato compilado desde Remix
        //carRentalContract = CarRentalContract.load(contractAddress, web3j, credentials, DefaultGasProvider())*/
    }

    // Cambiar Car por CarRentalContract
    fun getCarRentalContract(): Car {
        return carRentalContract
    }
}