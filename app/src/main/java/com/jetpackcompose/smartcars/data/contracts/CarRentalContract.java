package com.jetpackcompose.smartcars.data.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

@SuppressWarnings("rawtypes")
public class CarRentalContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610aa9806100206000396000f3fe6080604052600436106100555760003560e01c806317f956e01461005a57806340b8405a1461008357806396f19df81461009f5780639ca0fc0d146100ca578063d588dcdd14610107578063f3b5b80e14610147575b600080fd5b34801561006657600080fd5b50610081600480360381019061007c919061059f565b610175565b005b61009d6004803603810190610098919061062a565b610267565b005b3480156100ab57600080fd5b506100b46102f4565b6040516100c19190610666565b60405180910390f35b3480156100d657600080fd5b506100f160048036038101906100ec9190610681565b6102fa565b6040516100fe9190610666565b60405180910390f35b34801561011357600080fd5b5061012e6004803603810190610129919061059f565b61044f565b60405161013e94939291906106c1565b60405180910390f35b34801561015357600080fd5b5061015c6104d3565b60405161016c94939291906106c1565b60405180910390f35b60015481106101b9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101b090610763565b60405180910390fd5b60008060008381526020019081526020016000206003015414610211576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610208906107cf565b60405180910390fd5b42600080838152602001908152602001600020600301819055507f7527a7c26df04f31a2ae2dd18b7b9d394fa6ebe709e19e2ba0b886d4234ed88b814260405161025c9291906107ef565b60405180910390a150565b600034116102aa576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102a190610864565b60405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f193505050501580156102f0573d6000803e3d6000fd5b5050565b60015481565b600080831161033e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610335906108d0565b60405180910390fd5b60008211610381576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103789061093c565b60405180910390fd5b604051806080016040528084815260200183815260200142815260200160008152506000806001548152602001908152602001600020600082015181600001556020820151816001015560408201518160020155606082015181600301559050507facaf8450e0917288b2f048de09e9ed1cb781720156c2e232d23c888851e4b02060015484844260405161041994939291906106c1565b60405180910390a1600160008154809291906104349061098b565b91905055506001805461044791906109d3565b905092915050565b6000806000806001548510610499576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161049090610763565b60405180910390fd5b6000806000878152602001908152602001600020905080600001548160010154826002015483600301549450945094509450509193509193565b60008060008060006001541161051e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161051590610a53565b60405180910390fd5b60008060006001805461053191906109d3565b81526020019081526020016000209050806000015481600101548260020154836003015494509450945094505090919293565b600080fd5b6000819050919050565b61057c81610569565b811461058757600080fd5b50565b60008135905061059981610573565b92915050565b6000602082840312156105b5576105b4610564565b5b60006105c38482850161058a565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006105f7826105cc565b9050919050565b610607816105ec565b811461061257600080fd5b50565b600081359050610624816105fe565b92915050565b6000602082840312156106405761063f610564565b5b600061064e84828501610615565b91505092915050565b61066081610569565b82525050565b600060208201905061067b6000830184610657565b92915050565b6000806040838503121561069857610697610564565b5b60006106a68582860161058a565b92505060206106b78582860161058a565b9150509250929050565b60006080820190506106d66000830187610657565b6106e36020830186610657565b6106f06040830185610657565b6106fd6060830184610657565b95945050505050565b600082825260208201905092915050565b7f496e76616c69642072656e74616c206964000000000000000000000000000000600082015250565b600061074d601183610706565b915061075882610717565b602082019050919050565b6000602082019050818103600083015261077c81610740565b9050919050565b7f4361722068617320616c7265616479206265656e2072657475726e6564000000600082015250565b60006107b9601d83610706565b91506107c482610783565b602082019050919050565b600060208201905081810360008301526107e8816107ac565b9050919050565b60006040820190506108046000830185610657565b6108116020830184610657565b9392505050565b7f416d6f756e74206d7573742062652067726561746572207468616e2030000000600082015250565b600061084e601d83610706565b915061085982610818565b602082019050919050565b6000602082019050818103600083015261087d81610841565b9050919050565b7f436172206964206d7573742062652067726561746572207468616e2030000000600082015250565b60006108ba601d83610706565b91506108c582610884565b602082019050919050565b600060208201905081810360008301526108e9816108ad565b9050919050565b7f55736572206964206d7573742062652067726561746572207468616e20300000600082015250565b6000610926601e83610706565b9150610931826108f0565b602082019050919050565b6000602082019050818103600083015261095581610919565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061099682610569565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036109c8576109c761095c565b5b600182019050919050565b60006109de82610569565b91506109e983610569565b9250828203905081811115610a0157610a0061095c565b5b92915050565b7f4e6f2072656e74616c7320726567697374657265640000000000000000000000600082015250565b6000610a3d601583610706565b9150610a4882610a07565b602082019050919050565b60006020820190508181036000830152610a6c81610a30565b905091905056fea2646970667358221220a65952f4ecaa2a33dc9bb58b763f03e658bb39912b7cae90ae3b08c7da3fc4ce64736f6c63430008120033";

    public static final String FUNC_RENTCAR = "rentCar";

    public static final String FUNC_RETURNCAR = "returnCar";

    public static final String FUNC_UPDATEBALANCE = "updateBalance";

    public static final String FUNC_NUMRENTALS = "numRentals";

    public static final String FUNC_VIEWLASTRENTAL = "viewLastRental";

    public static final String FUNC_VIEWRENTAL = "viewRental";

    public static final Event CARRETURNED_EVENT = new Event("CarReturned", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RENTALREGISTERED_EVENT = new Event("RentalRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected CarRentalContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CarRentalContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CarRentalContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CarRentalContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    /*public static List<CarReturnedEventResponse> getCarReturnedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CARRETURNED_EVENT, transactionReceipt);
        ArrayList<CarReturnedEventResponse> responses = new ArrayList<CarReturnedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CarReturnedEventResponse typedResponse = new CarReturnedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.returnDate = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }*/

    public Flowable<CarReturnedEventResponse> carReturnedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CarReturnedEventResponse>() {
            @Override
            public CarReturnedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CARRETURNED_EVENT, log);
                CarReturnedEventResponse typedResponse = new CarReturnedEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.returnDate = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CarReturnedEventResponse> carReturnedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CARRETURNED_EVENT));
        return carReturnedEventFlowable(filter);
    }

    /*public static List<RentalRegisteredEventResponse> getRentalRegisteredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RENTALREGISTERED_EVENT, transactionReceipt);
        ArrayList<RentalRegisteredEventResponse> responses = new ArrayList<RentalRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RentalRegisteredEventResponse typedResponse = new RentalRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.carId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.userId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.rentalDate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }*/

    public Flowable<RentalRegisteredEventResponse> rentalRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RentalRegisteredEventResponse>() {
            @Override
            public RentalRegisteredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RENTALREGISTERED_EVENT, log);
                RentalRegisteredEventResponse typedResponse = new RentalRegisteredEventResponse();
                typedResponse.log = log;
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.carId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.userId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.rentalDate = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RentalRegisteredEventResponse> rentalRegisteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RENTALREGISTERED_EVENT));
        return rentalRegisteredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> rentCar(BigInteger _carId, BigInteger _userId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENTCAR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_carId), 
                new org.web3j.abi.datatypes.generated.Uint256(_userId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> returnCar(BigInteger _rentalId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RETURNCAR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rentalId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateBalance(String _admin, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> numRentals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NUMRENTALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> viewLastRental() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWLASTRENTAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> viewRental(BigInteger _rentalId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VIEWRENTAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rentalId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    @Deprecated
    public static CarRentalContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CarRentalContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CarRentalContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CarRentalContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CarRentalContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CarRentalContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CarRentalContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CarRentalContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class CarReturnedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public BigInteger returnDate;
    }

    public static class RentalRegisteredEventResponse extends BaseEventResponse {
        public BigInteger id;

        public BigInteger carId;

        public BigInteger userId;

        public BigInteger rentalDate;
    }
}
