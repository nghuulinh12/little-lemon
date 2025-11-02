package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val database = AppDatabase.getInstance(context)
    val menuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    var searchPhrase by remember { mutableStateOf("") }

//    val filteredItems = if (searchPhrase.isNotBlank()) {
//        menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
//    } else {
//        menuItems
//    }

    val categories = menuItems.map { it.category }.distinct()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val filteredItems = menuItems
        .filter {
            if (searchPhrase.isNotBlank()) it.title.contains(
                searchPhrase,
                ignoreCase = true
            ) else true
        }
        .filter { if (selectedCategory != null) it.category == selectedCategory else true }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        HeaderSection(
            searchText = searchPhrase,
            onSearchChange = { searchPhrase = it },
            onAvatarClick = { navController.navigate("Profile") }
        )

        CategorySection(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        MenuList(menuItems = filteredItems)

    }
}

@Composable
fun HeaderSection(searchText: String, onSearchChange: (String) -> Unit, onAvatarClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 34.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .height(50.dp)
                    .align(Alignment.Center)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_avatar),
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterEnd)
                    .clickable { onAvatarClick() }
            )
        }

        Column(
            modifier = Modifier
                .background(Color(0xFF495E57))
                .padding(16.dp)
        ) {
            Text(
                text = "Little Lemon",
                color = Color(0xFFF4CE14),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Chicago",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = searchText,
                onValueChange = onSearchChange,
                placeholder = { Text("Enter search phrase") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            )

        }
    }
}

@Composable
fun CategorySection(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "ORDER FOR DELIVERY!",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow() {
            items(categories) { category ->
                CategoryChip(
                    label = category,
                    isSelected = selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Composable
fun CategoryChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF495E57) else Color.LightGray,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = Modifier
            .padding(end = 8.dp)
            .height(40.dp)
    ) {
        Text(label)
    }
}

@Composable
fun MenuList(menuItems: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuItems) { item ->
            MenuItemRow(item)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemRow(item: MenuItemRoom) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(item.title, fontWeight = FontWeight.Bold)
            Text(
                item.description,
                maxLines = 2,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text("$${item.price}", fontWeight = FontWeight.Bold)
        }
        GlideImage(
            model = item.image,
            contentDescription = item.title,
            modifier = Modifier
                .size(80.dp)
                .padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}