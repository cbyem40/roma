package com.example.roma.service;

import com.example.roma.entity.Block;
import com.example.roma.utils.HashUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by rachel.tung on 2018/4/18.
 */
@Service
public class BlockChainService {

    public Block createBlock(String data, String previousHash){
        Block block = new Block();
        block.setData(data);
        block.setPreviousHash(previousHash);
        block.setTimeStamp(new Date().getTime());
        block = HashUtils.mineBlock(block);
        return block;
    }

}
