package com.example.roma.utils;

import com.example.roma.entity.Block;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by rachel.tung on 2018/4/18.
 */

@Component
public class HashUtils {

    static Gson gson;

    @Autowired
    Gson gsonObject ;

    static Integer difficulty = 3;

    @PostConstruct
    private void init(){
        HashUtils.gson = gsonObject;
    }

    public static String getSha256(String data){
        String sha256Hash = Hashing.sha256().hashString(data, StandardCharsets.UTF_8).toString();
        return sha256Hash;
    }

    public static String calcHash(Block block, Integer nonce){
        String hash = HashUtils.getSha256(block.getPreviousHash() + block.getTimeStamp() + nonce.toString() + block.getData());
        return hash;
    }

    public static Block mineBlock(Block block){
        String target = new String(new char[difficulty]).replace('\0', '0');
        Integer nonce = 0;
        String hashResult;

        do{
            nonce ++;
            hashResult = calcHash(block, nonce);
        }while(!hashResult.substring(0, difficulty).equals(target));

        System.out.println("Block mined... nonce: " + nonce );
        System.out.println("hash: " + hashResult);
        block.setNonce(nonce);
        block.setHash(hashResult);

        return block;
    }




    public static Boolean checkBlockChainValid(String blockChainJson){
        Block[] blocks = gson.fromJson(blockChainJson, Block[].class);
        String target = new String(new char[difficulty]).replace('\0', '0');

        for(int i=0; i < blocks.length; i++){

            Block currentBlock = blocks[i];
            Block prevBlock = blocks[i];

            // check the current hash is correct
            String calcHash = HashUtils.calcHash(currentBlock, currentBlock.getNonce());
            if (!currentBlock.getHash().equals(calcHash))
                return false;

            // check if the current hash has enough 0 as prefix
            if (!calcHash.substring(0, difficulty).equals(target))
                return false;

            // check if the previous hash is correct
            if (i == 0){
                if (!blocks[i].getPreviousHash().equals("0"))
                    return false;
            }else{
                if (!blocks[i].getPreviousHash().equals(blocks[i-1].getHash()))
                    return false;
            }
        }
        return true;
    }
}
