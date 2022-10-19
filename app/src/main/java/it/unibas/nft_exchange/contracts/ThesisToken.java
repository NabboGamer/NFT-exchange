package it.unibas.nft_exchange.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class ThesisToken extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b506040518060400160405280600b81526020016a2a3432b9b4b9aa37b5b2b760a91b8152506040518060400160405280600381526020016254544b60e81b815250816000908162000063919062000190565b50600162000072828262000190565b5050506200008f620000896200009560201b60201c565b62000099565b6200025c565b3390565b600b80546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b634e487b7160e01b600052604160045260246000fd5b600181811c908216806200011657607f821691505b6020821081036200013757634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156200018b57600081815260208120601f850160051c81016020861015620001665750805b601f850160051c820191505b81811015620001875782815560010162000172565b5050505b505050565b81516001600160401b03811115620001ac57620001ac620000eb565b620001c481620001bd845462000101565b846200013d565b602080601f831160018114620001fc5760008415620001e35750858301515b600019600386901b1c1916600185901b17855562000187565b600085815260208120601f198616915b828110156200022d578886015182559484019460019091019084016200020c565b50858210156200024c5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b611d7c806200026c6000396000f3fe608060405234801561001057600080fd5b50600436106101375760003560e01c80636352211e116100b8578063a22cb4651161007c578063a22cb46514610271578063b88d4fde14610284578063c87b56dd14610297578063d204c45e146102aa578063e985e9c5146102bd578063f2fde38b146102f957600080fd5b80636352211e1461022a57806370a082311461023d578063715018a6146102505780638da5cb5b1461025857806395d89b411461026957600080fd5b806323b872dd116100ff57806323b872dd146101cb5780632f745c59146101de57806342842e0e146101f157806342966c68146102045780634f6ccce71461021757600080fd5b806301ffc9a71461013c57806306fdde0314610164578063081812fc14610179578063095ea7b3146101a457806318160ddd146101b9575b600080fd5b61014f61014a366004611707565b61030c565b60405190151581526020015b60405180910390f35b61016c61031d565b60405161015b9190611774565b61018c610187366004611787565b6103af565b6040516001600160a01b03909116815260200161015b565b6101b76101b23660046117bc565b6103d6565b005b6008545b60405190815260200161015b565b6101b76101d93660046117e6565b6104f0565b6101bd6101ec3660046117bc565b610522565b6101b76101ff3660046117e6565b6105b8565b6101b7610212366004611787565b6105d3565b6101bd610225366004611787565b610604565b61018c610238366004611787565b610697565b6101bd61024b366004611822565b6106f7565b6101b761077d565b600b546001600160a01b031661018c565b61016c610791565b6101b761027f36600461183d565b6107a0565b6101b7610292366004611905565b6107af565b61016c6102a5366004611787565b6107e7565b6101b76102b8366004611981565b6107f2565b61014f6102cb3660046119e3565b6001600160a01b03918216600090815260056020908152604080832093909416825291909152205460ff1690565b6101b7610307366004611822565b610829565b60006103178261089f565b92915050565b60606000805461032c90611a16565b80601f016020809104026020016040519081016040528092919081815260200182805461035890611a16565b80156103a55780601f1061037a576101008083540402835291602001916103a5565b820191906000526020600020905b81548152906001019060200180831161038857829003601f168201915b5050505050905090565b60006103ba826108c4565b506000908152600460205260409020546001600160a01b031690565b60006103e182610697565b9050806001600160a01b0316836001600160a01b0316036104535760405162461bcd60e51b815260206004820152602160248201527f4552433732313a20617070726f76616c20746f2063757272656e74206f776e656044820152603960f91b60648201526084015b60405180910390fd5b336001600160a01b038216148061046f575061046f81336102cb565b6104e15760405162461bcd60e51b815260206004820152603e60248201527f4552433732313a20617070726f76652063616c6c6572206973206e6f7420746f60448201527f6b656e206f776e6572206e6f7220617070726f76656420666f7220616c6c0000606482015260840161044a565b6104eb8383610923565b505050565b6104fb335b82610991565b6105175760405162461bcd60e51b815260040161044a90611a50565b6104eb838383610a10565b600061052d836106f7565b821061058f5760405162461bcd60e51b815260206004820152602b60248201527f455243373231456e756d657261626c653a206f776e657220696e646578206f7560448201526a74206f6620626f756e647360a81b606482015260840161044a565b506001600160a01b03919091166000908152600660209081526040808320938352929052205490565b6104eb838383604051806020016040528060008152506107af565b6105dc336104f5565b6105f85760405162461bcd60e51b815260040161044a90611a50565b61060181610bb7565b50565b600061060f60085490565b82106106725760405162461bcd60e51b815260206004820152602c60248201527f455243373231456e756d657261626c653a20676c6f62616c20696e646578206f60448201526b7574206f6620626f756e647360a01b606482015260840161044a565b6008828154811061068557610685611a9e565b90600052602060002001549050919050565b6000818152600260205260408120546001600160a01b0316806103175760405162461bcd60e51b8152602060048201526018602482015277115490cdcc8c4e881a5b9d985b1a59081d1bdad95b88125160421b604482015260640161044a565b60006001600160a01b0382166107615760405162461bcd60e51b815260206004820152602960248201527f4552433732313a2061646472657373207a65726f206973206e6f7420612076616044820152683634b21037bbb732b960b91b606482015260840161044a565b506001600160a01b031660009081526003602052604090205490565b610785610bc0565b61078f6000610c1a565b565b60606001805461032c90611a16565b6107ab338383610c6c565b5050565b6107b93383610991565b6107d55760405162461bcd60e51b815260040161044a90611a50565b6107e184848484610d3a565b50505050565b606061031782610d6d565b6107fa610bc0565b6000610805600c5490565b9050610815600c80546001019055565b61081f8382610e75565b6104eb8183610e8f565b610831610bc0565b6001600160a01b0381166108965760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b606482015260840161044a565b61060181610c1a565b60006001600160e01b0319821663780e9d6360e01b1480610317575061031782610f22565b6000818152600260205260409020546001600160a01b03166106015760405162461bcd60e51b8152602060048201526018602482015277115490cdcc8c4e881a5b9d985b1a59081d1bdad95b88125160421b604482015260640161044a565b600081815260046020526040902080546001600160a01b0319166001600160a01b038416908117909155819061095882610697565b6001600160a01b03167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45050565b60008061099d83610697565b9050806001600160a01b0316846001600160a01b031614806109e457506001600160a01b0380821660009081526005602090815260408083209388168352929052205460ff165b80610a085750836001600160a01b03166109fd846103af565b6001600160a01b0316145b949350505050565b826001600160a01b0316610a2382610697565b6001600160a01b031614610a875760405162461bcd60e51b815260206004820152602560248201527f4552433732313a207472616e736665722066726f6d20696e636f72726563742060448201526437bbb732b960d91b606482015260840161044a565b6001600160a01b038216610ae95760405162461bcd60e51b8152602060048201526024808201527f4552433732313a207472616e7366657220746f20746865207a65726f206164646044820152637265737360e01b606482015260840161044a565b610af4838383610f72565b610aff600082610923565b6001600160a01b0383166000908152600360205260408120805460019290610b28908490611aca565b90915550506001600160a01b0382166000908152600360205260408120805460019290610b56908490611add565b909155505060008181526002602052604080822080546001600160a01b0319166001600160a01b0386811691821790925591518493918716917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b61060181610f7d565b600b546001600160a01b0316331461078f5760405162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015260640161044a565b600b80546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b816001600160a01b0316836001600160a01b031603610ccd5760405162461bcd60e51b815260206004820152601960248201527f4552433732313a20617070726f766520746f2063616c6c657200000000000000604482015260640161044a565b6001600160a01b03838116600081815260056020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b610d45848484610a10565b610d5184848484610fbd565b6107e15760405162461bcd60e51b815260040161044a90611af0565b6060610d78826108c4565b6000828152600a602052604081208054610d9190611a16565b80601f0160208091040260200160405190810160405280929190818152602001828054610dbd90611a16565b8015610e0a5780601f10610ddf57610100808354040283529160200191610e0a565b820191906000526020600020905b815481529060010190602001808311610ded57829003601f168201915b505050505090506000610e2860408051602081019091526000815290565b90508051600003610e3a575092915050565b815115610e6c578082604051602001610e54929190611b42565b60405160208183030381529060405292505050919050565b610a08846110be565b6107ab828260405180602001604052806000815250611132565b6000828152600260205260409020546001600160a01b0316610f0a5760405162461bcd60e51b815260206004820152602e60248201527f45524337323155524953746f726167653a2055524920736574206f66206e6f6e60448201526d32bc34b9ba32b73a103a37b5b2b760911b606482015260840161044a565b6000828152600a602052604090206104eb8282611bbf565b60006001600160e01b031982166380ac58cd60e01b1480610f5357506001600160e01b03198216635b5e139f60e01b145b8061031757506301ffc9a760e01b6001600160e01b0319831614610317565b6104eb838383611165565b610f868161121d565b6000818152600a602052604090208054610f9f90611a16565b159050610601576000818152600a60205260408120610601916116a3565b60006001600160a01b0384163b156110b357604051630a85bd0160e11b81526001600160a01b0385169063150b7a0290611001903390899088908890600401611c7f565b6020604051808303816000875af192505050801561103c575060408051601f3d908101601f1916820190925261103991810190611cbc565b60015b611099573d80801561106a576040519150601f19603f3d011682016040523d82523d6000602084013e61106f565b606091505b5080516000036110915760405162461bcd60e51b815260040161044a90611af0565b805181602001fd5b6001600160e01b031916630a85bd0160e11b149050610a08565b506001949350505050565b60606110c9826108c4565b60006110e060408051602081019091526000815290565b90506000815111611100576040518060200160405280600081525061112b565b8061110a846112c4565b60405160200161111b929190611b42565b6040516020818303038152906040525b9392505050565b61113c83836113c5565b6111496000848484610fbd565b6104eb5760405162461bcd60e51b815260040161044a90611af0565b6001600160a01b0383166111c0576111bb81600880546000838152600960205260408120829055600182018355919091527ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee30155565b6111e3565b816001600160a01b0316836001600160a01b0316146111e3576111e38382611513565b6001600160a01b0382166111fa576104eb816115b0565b826001600160a01b0316826001600160a01b0316146104eb576104eb828261165f565b600061122882610697565b905061123681600084610f72565b611241600083610923565b6001600160a01b038116600090815260036020526040812080546001929061126a908490611aca565b909155505060008281526002602052604080822080546001600160a01b0319169055518391906001600160a01b038416907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908390a45050565b6060816000036112eb5750506040805180820190915260018152600360fc1b602082015290565b8160005b811561131557806112ff81611cd9565b915061130e9050600a83611d08565b91506112ef565b60008167ffffffffffffffff81111561133057611330611879565b6040519080825280601f01601f19166020018201604052801561135a576020820181803683370190505b5090505b8415610a085761136f600183611aca565b915061137c600a86611d1c565b611387906030611add565b60f81b81838151811061139c5761139c611a9e565b60200101906001600160f81b031916908160001a9053506113be600a86611d08565b945061135e565b6001600160a01b03821661141b5760405162461bcd60e51b815260206004820181905260248201527f4552433732313a206d696e7420746f20746865207a65726f2061646472657373604482015260640161044a565b6000818152600260205260409020546001600160a01b0316156114805760405162461bcd60e51b815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e74656400000000604482015260640161044a565b61148c60008383610f72565b6001600160a01b03821660009081526003602052604081208054600192906114b5908490611add565b909155505060008181526002602052604080822080546001600160a01b0319166001600160a01b03861690811790915590518392907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b60006001611520846106f7565b61152a9190611aca565b60008381526007602052604090205490915080821461157d576001600160a01b03841660009081526006602090815260408083208584528252808320548484528184208190558352600790915290208190555b5060009182526007602090815260408084208490556001600160a01b039094168352600681528383209183525290812055565b6008546000906115c290600190611aca565b600083815260096020526040812054600880549394509092849081106115ea576115ea611a9e565b90600052602060002001549050806008838154811061160b5761160b611a9e565b600091825260208083209091019290925582815260099091526040808220849055858252812055600880548061164357611643611d30565b6001900381819060005260206000200160009055905550505050565b600061166a836106f7565b6001600160a01b039093166000908152600660209081526040808320868452825280832085905593825260079052919091209190915550565b5080546116af90611a16565b6000825580601f106116bf575050565b601f01602090049060005260206000209081019061060191905b808211156116ed57600081556001016116d9565b5090565b6001600160e01b03198116811461060157600080fd5b60006020828403121561171957600080fd5b813561112b816116f1565b60005b8381101561173f578181015183820152602001611727565b50506000910152565b60008151808452611760816020860160208601611724565b601f01601f19169290920160200192915050565b60208152600061112b6020830184611748565b60006020828403121561179957600080fd5b5035919050565b80356001600160a01b03811681146117b757600080fd5b919050565b600080604083850312156117cf57600080fd5b6117d8836117a0565b946020939093013593505050565b6000806000606084860312156117fb57600080fd5b611804846117a0565b9250611812602085016117a0565b9150604084013590509250925092565b60006020828403121561183457600080fd5b61112b826117a0565b6000806040838503121561185057600080fd5b611859836117a0565b91506020830135801515811461186e57600080fd5b809150509250929050565b634e487b7160e01b600052604160045260246000fd5b600067ffffffffffffffff808411156118aa576118aa611879565b604051601f8501601f19908116603f011681019082821181831017156118d2576118d2611879565b816040528093508581528686860111156118eb57600080fd5b858560208301376000602087830101525050509392505050565b6000806000806080858703121561191b57600080fd5b611924856117a0565b9350611932602086016117a0565b925060408501359150606085013567ffffffffffffffff81111561195557600080fd5b8501601f8101871361196657600080fd5b6119758782356020840161188f565b91505092959194509250565b6000806040838503121561199457600080fd5b61199d836117a0565b9150602083013567ffffffffffffffff8111156119b957600080fd5b8301601f810185136119ca57600080fd5b6119d98582356020840161188f565b9150509250929050565b600080604083850312156119f657600080fd5b6119ff836117a0565b9150611a0d602084016117a0565b90509250929050565b600181811c90821680611a2a57607f821691505b602082108103611a4a57634e487b7160e01b600052602260045260246000fd5b50919050565b6020808252602e908201527f4552433732313a2063616c6c6572206973206e6f7420746f6b656e206f776e6560408201526d1c881b9bdc88185c1c1c9bdd995960921b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b8181038181111561031757610317611ab4565b8082018082111561031757610317611ab4565b60208082526032908201527f4552433732313a207472616e7366657220746f206e6f6e20455243373231526560408201527131b2b4bb32b91034b6b83632b6b2b73a32b960711b606082015260800190565b60008351611b54818460208801611724565b835190830190611b68818360208801611724565b01949350505050565b601f8211156104eb57600081815260208120601f850160051c81016020861015611b985750805b601f850160051c820191505b81811015611bb757828155600101611ba4565b505050505050565b815167ffffffffffffffff811115611bd957611bd9611879565b611bed81611be78454611a16565b84611b71565b602080601f831160018114611c225760008415611c0a5750858301515b600019600386901b1c1916600185901b178555611bb7565b600085815260208120601f198616915b82811015611c5157888601518255948401946001909101908401611c32565b5085821015611c6f5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b6001600160a01b0385811682528416602082015260408101839052608060608201819052600090611cb290830184611748565b9695505050505050565b600060208284031215611cce57600080fd5b815161112b816116f1565b600060018201611ceb57611ceb611ab4565b5060010190565b634e487b7160e01b600052601260045260246000fd5b600082611d1757611d17611cf2565b500490565b600082611d2b57611d2b611cf2565b500690565b634e487b7160e01b600052603160045260246000fdfea2646970667358221220fcc95a607277d6453ed683212119c2f1806ed273f3d3d09e09ce18cf454170c164736f6c63430008110033";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SAFEMINT = "safeMint";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENBYINDEX = "tokenByIndex";

    public static final String FUNC_TOKENOFOWNERBYINDEX = "tokenOfOwnerByIndex";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected ThesisToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ThesisToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ThesisToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ThesisToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burn(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BURN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.Address(160, operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeMint(String to, String uri) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAFEMINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.Utf8String(uri)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_safeTransferFrom, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator), 
                new org.web3j.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> tokenByIndex(BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENBYINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> tokenOfOwnerByIndex(String owner, BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENOFOWNERBYINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENURI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ThesisToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ThesisToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ThesisToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ThesisToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ThesisToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ThesisToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ThesisToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ThesisToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ThesisToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ThesisToken.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<ThesisToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ThesisToken.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ThesisToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ThesisToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ThesisToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ThesisToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }
}
