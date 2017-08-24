package io.nem.apps.main;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.nem.core.connect.client.NisApiId;
import org.nem.core.model.ncc.AccountMetaDataPair;
import org.nem.core.model.ncc.UnconfirmedTransactionMetaDataPair;
import org.nem.core.serialization.Deserializer;

import io.nem.apps.api.AccountApi;
import io.nem.apps.builders.ConfigurationBuilder;
import io.nem.apps.service.Globals;

public class AccountApiTest extends ApiUnitTest {
	

	@Test
	public void testDeserializeAccount() {
		
		try {
			final CompletableFuture<Deserializer> des = Globals.CONNECTOR.getAsync(Globals.getNodeEndpoint(),
					NisApiId.NIS_REST_ACCOUNT_LOOK_UP, "address=MDVJCH6F5FXVUOFCC3PZTSXPQNPCULYQMWEGAOOW");

			des.thenAcceptAsync(d -> {

				System.out.println(new AccountMetaDataPair(d).getEntity().getBalance());
			}).exceptionally(e -> {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}).get();

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testDeserializeAccountPk() {
		try {
			final CompletableFuture<Deserializer> des = Globals.CONNECTOR.getAsync(Globals.getNodeEndpoint(),
					NisApiId.NIS_REST_ACCOUNT_UNCONFIRMED, "address=MDVJCH6F5FXVUOFCC3PZTSXPQNPCULYQMWEGAOOW");

			des.thenAcceptAsync(d -> {
				System.out.println(d.readObjectArray("data", UnconfirmedTransactionMetaDataPair::new).size());
				assertTrue(d.readObjectArray("data", UnconfirmedTransactionMetaDataPair::new).size() > 0);
			}).exceptionally(e -> {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return null;
			}).get();
			assert (false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAccountApiAddress() {
		System.out.println(
				AccountApi.getAccountByAddress("MDVJCH6F5FXVUOFCC3PZTSXPQNPCULYQMWEGAOOW").getEntity().getBalance());
	}

	@Test
	@Ignore
	public void testAccountApiAllTransaction() {
		System.out.println(AccountApi.getAllTransactions("MDVJCH6F5FXVUOFCC3PZTSXPQNPCULYQMWEGAOOW").size());
	}

	@Test
	@Ignore
	public void testAccountApiAllOwnedMosaic() {
		System.out.println(AccountApi.getAccountOwnedMosaic("MDVJCH6F5FXVUOFCC3PZTSXPQNPCULYQMWEGAOOW"));
	}

	@Test
	public void testGenerteNewAccount() {
		System.out.println(AccountApi.generateAccount().getNetworkVersion());
	}
}