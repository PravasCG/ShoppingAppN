package com.shoppy.botpassignment.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.capgemini.botpassignmentpravas.R
import com.shoppy.botpassignment.ShoppingAppUtils
import com.shoppy.botpassignment.components.ProductCard
import com.shoppy.botpassignment.components.ShoppingAppBar2
import com.shoppy.botpassignment.models.MBrand
import com.shoppy.botpassignment.models.MProducts
import com.shoppy.botpassignment.navigation.BottomNavScreens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
    onAddToCart: (product: MProducts) -> Unit
) {
    val userNameState = remember { mutableStateOf<String?>("") }
    val imageState = remember { mutableStateOf<String?>("") }


    //getting username from firebase
    viewModel.getUserNameAndImage(profile_image = { imageState.value = it }) {
        userNameState.value = it
    }

    var listOfBrands = emptyList<MProducts>()

    if (!viewModel.fireDataBS.value.data.isNullOrEmpty()) {
        listOfBrands = viewModel.fireDataBS.value.data!!.toList()

    }

    //Pull to Refresh Boolean
    val refreshing = viewModel.isLoading
    //Pull to Refresh State
    val refreshState = rememberPullRefreshState(refreshing = refreshing.value, onRefresh = {
        viewModel.pullToRefresh(navHostController = navController)
    })

    Scaffold(
        topBar = {
            ShoppingAppBar2(
                userName = userNameState.value,
                profile_url = imageState.value,
                navHostController = navController
            ) {

                //Navigating to Search Screen
                navController.navigate(BottomNavScreens.SearchScreen.route)
            }
        },
        backgroundColor = ShoppingAppUtils.offWhite
    ) { innerPadding ->

        Box(
            modifier = Modifier.pullRefresh(state = refreshState),
            contentAlignment = Alignment.TopCenter
        ) {

            if (!refreshing.value) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //product card

                    ProductCard(
                        cardItem = listOfBrands,
                        navController = navController,
                        onAddToCartClick = onAddToCart
                    )

                    Spacer(modifier = Modifier.height(120.dp))
                }
                //This Column is required to Center the refresh Icon
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { }
            }
            PullRefreshIndicator(
                refreshing = refreshing.value,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        }

    }
}

//Brand List LazyRow
@Composable
fun BrandsList(brands: List<MBrand>) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)) {
        items(items = brands) { images ->
            BrandCard(brand = images)
        }
    }
}

//Single Card For Brands
@Composable
fun BrandCard(brand: MBrand) {
    Card(
        modifier = Modifier
            .height(58.dp)
            .width(88.dp)
            .padding(6.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(12.dp)
    ) {

        AsyncImage(
            model = brand.logo,
            contentDescription =  brand.brand_name,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder), // Shown while loading
            error = painterResource(id = R.drawable.placeholder), // Shown if loading fails
        )


    }
}