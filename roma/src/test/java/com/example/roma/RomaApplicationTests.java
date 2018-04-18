package com.example.roma;

import com.example.roma.entity.Block;
import com.example.roma.service.BlockChainService;
import com.example.roma.utils.HashUtils;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RomaApplicationTests {

	@Autowired
	private BlockChainService blockChainService;

	@Autowired
	private Gson gson;

	@Test
//    @Ignore
	public void testCreateBlocks() {
		Block block1 = blockChainService.createBlock("Hola", "0");
		System.out.println("First block hash: " + block1.getHash());
    }

    @Test
//    @Ignore
    public void testValidBlock(){
	    ArrayList blockChain = new ArrayList<>();

	    Block block1 = blockChainService.createBlock("Roma", "0");
        Block block2 = blockChainService.createBlock("Salerno", block1.getHash());
        Block block3 = blockChainService.createBlock("Sorrento", block2.getHash());

        blockChain.add(block1);
        blockChain.add(block2);
        blockChain.add(block3);

        String blockChainJson = gson.toJson(blockChain);
        Assert.assertTrue(HashUtils.checkBlockChainValid(blockChainJson));

        block2.setData("fake data");
        ArrayList hackedBlockChain = new ArrayList<>();
        hackedBlockChain.add(block1);
        hackedBlockChain.add(block2);
        hackedBlockChain.add(block3);
        String hackedBlockChainJson = gson.toJson(hackedBlockChain);
        Assert.assertFalse(HashUtils.checkBlockChainValid(hackedBlockChainJson));
    }




}
