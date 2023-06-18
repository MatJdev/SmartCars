package com.jetpackcompose.smartcars.model

import com.jetpackcompose.smartcars.data.contracts.CarRentalContract
import org.web3j.protocol.Web3j
import org.web3j.crypto.Credentials
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider

object Web3jSingleton {
    private lateinit var web3j: Web3j
    private lateinit var credentials: Credentials
    private lateinit var carRentalContract: CarRentalContract

    fun init() {
        // Inicializar Web3j
        val infuraUrl = "https://sepolia.infura.io/v3/f9513e670ca7464ea55d2e55f378ff93" // your infura URL here
        val httpProvider = HttpService(infuraUrl)
        web3j = Web3j.build(httpProvider)

        // Inicializar credenciales
        val privateKey = "" // your private key here
        credentials = Credentials.create(privateKey)

        // Inicializar instancia del contrato
        val contractAddress = "0x2cBD18e1A2640c7C12D89E7F8431E02D6720AE87" // your contract address here
        carRentalContract = CarRentalContract.load(contractAddress, web3j, credentials, DefaultGasProvider())
    }

    fun getCarRentalContract(): CarRentalContract {
        return carRentalContract
    }
}